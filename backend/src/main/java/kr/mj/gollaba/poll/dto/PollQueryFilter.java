package kr.mj.gollaba.poll.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PollQueryFilter {

    private final Long userId;

    private final String title;

    private final Integer offset;

    private final Integer limit;

    @Builder
    private PollQueryFilter(Long userId, String title, Integer offset, Integer limit) {
        this.userId = userId;
        this.title = title;
        this.offset = offset;
        this.limit = limit;
    }

}
