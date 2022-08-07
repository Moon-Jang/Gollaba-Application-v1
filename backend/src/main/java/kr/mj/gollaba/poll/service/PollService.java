package kr.mj.gollaba.poll.service;

import kr.mj.gollaba.common.util.CryptUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.dto.*;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Voter voter = request.toEntity(cryptUtils);

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER));
            voter.registerUser(user);
        }

        Poll poll = pollQueryRepository.findById(request.getPollId())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        poll.vote(request.getOptionId(), voter);

        pollRepository.save(poll);
    }
}
