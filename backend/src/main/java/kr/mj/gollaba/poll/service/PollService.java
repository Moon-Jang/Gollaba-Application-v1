package kr.mj.gollaba.poll.service;

import kr.mj.gollaba.common.service.S3UploadService;
import kr.mj.gollaba.common.util.CryptUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.dto.*;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollQueryRepository pollQueryRepository;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final CryptUtils cryptUtils;
    private final static String ANONYMOUS_NAME = "익명";
    public static final String POLL_IMAGE_S3_PATH = "profile_image";

    @Transactional
    public CreatePollResponse create(CreatePollRequest request) {
        request.validate();
        Poll poll = request.toEntity();

        if (request.getUserId() != null) {
            User creator = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER));

            poll.registerCreator(creator);
        }

        Poll savedPoll = pollRepository.save(poll);

        if (request.getPollImage() != null) {
            String imageUrl = uploadPollImage(savedPoll.getId(), request.getPollImage());
            savedPoll.updatePollImageUrl(imageUrl);
            pollRepository.save(savedPoll);
        }

        return new CreatePollResponse(savedPoll.getId());
    }

    public FindAllPollResponse findAll(FindAllPollRequest request) {
        request.validate();
        PollQueryFilter filter = request.toFilter();
        final long totalCount = pollQueryRepository.findAllCount(filter);
        List<Long> ids = pollQueryRepository.findIds(filter);
        List<Poll> polls = pollQueryRepository.findAll(ids);

        return new FindAllPollResponse(totalCount, polls);
    }

    public FindPollResponse find(Long pollId) {
        Poll poll = pollQueryRepository.findById(pollId)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        if (poll == null) {
            throw new GollabaException(GollabaErrorCode.NOT_EXIST_POLL);
        }

        return new FindPollResponse(poll);
    }

    public void update(UpdatePollRequest request, User user) {
        Poll poll = pollQueryRepository.findById(request.getPollId())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        if (poll.getUser().getId().equals(user.getId()) == false) {
            throw new GollabaException(GollabaErrorCode.NOT_EQUAL_POLL_CREATOR);
        }

        if (request.getTitle() != null) {
            poll.updateTitle(request.getTitle());
        }

        if (request.getPollImage() != null) {
            String imageUrl = uploadPollImage(poll.getId(), request.getPollImage());
            poll.updatePollImageUrl(imageUrl);
        }

        if (request.getOptions() != null) {
            request.getOptions().stream()
                    .map(UpdatePollRequest.OptionDto::toEntity)
                    .forEach(option -> poll.addOption(option));
        }

        if (request.getEndedAt() != null) {
            poll.updateEndedAt(request.getEndedAt());
        }

        pollRepository.save(poll);
    }

    public void vote(VoteRequest request) {
        request.validate();
        User user = null;
        Poll poll = pollQueryRepository.findById(request.getPollId())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        validateVote(poll, request);

        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER));
        }

        for (Long optionId : request.getOptionIds()) {
            Voter voter = Voter.builder()
                    .voterName(request.getVoterName() != null ? request.getVoterName() : generateAnonymousName(poll))
                    .ipAddress(cryptUtils.encrypt(request.getIpAddress()))
                    .build();

            if (user != null) {
                voter.registerUser(user);
            }

            poll.vote(optionId, voter);
        }

        pollRepository.save(poll);
    }

    private String generateAnonymousName(Poll poll) {
        final int count = (int) poll.getOptions().stream()
                .flatMap(el -> el.getVoters().stream())
                .count();

        return ANONYMOUS_NAME + (count + 1);
    }

    private void validateVote(Poll poll, VoteRequest request) {
        if (poll.getResponseType() == PollingResponseType.SINGLE && request.getOptionIds().size() > 1) {
            throw new GollabaException(GollabaErrorCode.NOT_AVAILABLE_MULTI_VOTE_BY_RESPONSE_TYPE);
        }

        if (poll.getIsBallot() && request.getVoterName() != null) {
            throw new GollabaException(GollabaErrorCode.DONT_NEED_VOTER_NAME);
        }

        final boolean isAlreadyVote = poll.getOptions()
                .stream()
                .flatMap(option -> option.getVoters().stream())
                .filter(voter -> request.getOptionIds().contains(voter.getOption().getId()))
                .map(voter -> cryptUtils.decrypt(voter.getIpAddress()))
                .anyMatch(address -> address.equals(request.getIpAddress()));

        if (isAlreadyVote) {
            throw new GollabaException(GollabaErrorCode.ALREADY_VOTE);
        }
    }

    private String uploadPollImage(long pollId, MultipartFile pollImage) {
        String fileName = s3UploadService.generateFileName(pollId, pollImage.getContentType());
        String imageUrl = s3UploadService.upload(POLL_IMAGE_S3_PATH, fileName, pollImage);
        return imageUrl;
    }
}
