package kr.mj.gollaba.poll.service;

import kr.mj.gollaba.favorites.repository.FavoritesRepository;
import kr.mj.gollaba.poll.dto.PollQueryFilter;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.PollDailyStats;
import kr.mj.gollaba.poll.entity.PollStats;
import kr.mj.gollaba.poll.repository.PollDailyStatsRepository;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.poll.repository.PollStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static kr.mj.gollaba.poll.dto.PollQueryFilter.emptyFilter;

@Service
@RequiredArgsConstructor
public class PollStatsService {
    private final PollQueryRepository pollQueryRepository;
    private final PollStatsRepository pollStatsRepository;
    private final PollDailyStatsRepository pollDailyStatsRepository;
    private final FavoritesRepository favoritesRepository;

    @Transactional
    public void produceStats(LocalDate date) {
        var pollCnt = pollQueryRepository.findAllCount(emptyFilter());
        var chunkSize = 500;
        var loopCnt = pollCnt / chunkSize;

        for (int i = 0; i <= loopCnt; i++) {
            var filter = PollQueryFilter.builder()
                .offset(i * chunkSize)
                .limit(chunkSize)
                .build();
            var pollIds = pollQueryRepository.findIds(filter);
            var polls = pollQueryRepository.findAll(pollIds);
            var pollStatsList = polls.stream()
                .map(poll -> findOrCreatePollStats(poll))
                .collect(Collectors.toList());

            for (var pollStat : pollStatsList) {
                var poll = pollStat.getPoll();
                var favoritesCnt= favoritesRepository.countByPollId(poll.getId());

                var pollDailyStats = PollDailyStats.builder()
                    .poll(poll)
                    .date(date)
                    .dailyVoteCount(poll.getVoteCnt() - pollStat.getTotalVoteCount())
                    .dailyReadCount(poll.getReadCount() - pollStat.getTotalReadCount())
                    .dailyFavoritesCount(favoritesCnt - pollStat.getTotalFavoritesCount())
                    .build();

                pollDailyStatsRepository.save(pollDailyStats);

                pollStat.updateTotalReadCount(poll.getReadCount());
                pollStat.updateTotalVoteCount(poll.getVoteCnt());
                pollStat.updateTotalFavoritesCount(favoritesCnt);

                pollStatsRepository.save(pollStat);
            }
        }
    }

    private PollStats findOrCreatePollStats(Poll poll) {
        return pollStatsRepository.findByPollId(poll.getId())
            .orElseGet(() ->
                pollStatsRepository.save(PollStats.create(poll))
            );
    }
}
