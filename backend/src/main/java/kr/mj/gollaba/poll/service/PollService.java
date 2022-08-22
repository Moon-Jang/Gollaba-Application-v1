package kr.mj.gollaba.poll.service;

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
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollQueryRepository pollQueryRepository;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;
    private final CryptUtils cryptUtils;

    public CreatePollResponse create(CreatePollRequest request) {
        request.validate();
        Poll poll = request.toEntity();

        if (request.getUserId() != null) {
            User creator = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER));

            poll.registerCreator(creator);
        }

        Long pollId = pollRepository.save(poll).getId();

        return new CreatePollResponse(pollId);
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
                    .voterName(request.getVoterName() != null ? request.getVoterName() : request.generateVoterName(poll))
                    .ipAddress(cryptUtils.encrypt(request.getIpAddress()))
                    .build();

            if (user != null) {
                voter.registerUser(user);
            }

            poll.vote(optionId, voter);
        }

        pollRepository.save(poll);
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

}
