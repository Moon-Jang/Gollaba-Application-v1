package kr.mj.gollaba.poll.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.dto.CreatePollRequest;
import kr.mj.gollaba.poll.dto.FindAllPollRequest;
import kr.mj.gollaba.poll.dto.FindAllPollResponse;
import kr.mj.gollaba.poll.dto.FindPollResponse;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollQueryRepository pollQueryRepository;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    public void create(CreatePollRequest request) {
        request.validate();
        Poll poll = request.toDto();

        if (request.getUserId() != null) {
            User creator = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER));

            poll.registerCreator(creator);
        }

        pollRepository.save(poll);
    }

    public FindAllPollResponse findAll(FindAllPollRequest request) {
        request.validate();
        List<Poll> polls = pollQueryRepository.findAll(request.toFilter());

        return new FindAllPollResponse(polls);
    }

    public FindPollResponse find(Long pollId) {
        Poll poll = pollQueryRepository.findById(pollId);

        return new FindPollResponse(poll);
    }
}
