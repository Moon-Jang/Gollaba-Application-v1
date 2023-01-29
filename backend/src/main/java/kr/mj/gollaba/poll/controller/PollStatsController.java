package kr.mj.gollaba.poll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.ErrorAPIResponse;
import kr.mj.gollaba.poll.dto.FindAllPollResponse;
import kr.mj.gollaba.poll.service.PollStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Const.ROOT_URL)
@Api(tags = "Poll-Stats")
@RequiredArgsConstructor
public class PollStatsController {

    private final PollStatsService pollStatsService;

    @ApiOperation(value = "투표 인기 순위 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = FindAllPollResponse.class))),
        @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @GetMapping(path = "/polls/top")
    public ResponseEntity<FindAllPollResponse> findAllForTop() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(pollStatsService.findAllForTop());
    }

    @ApiOperation(value = "투표 실시간 인기 순위 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = FindAllPollResponse.class))),
        @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @GetMapping(path = "/polls/trending")
    public ResponseEntity<FindAllPollResponse> findAllForTrending() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(pollStatsService.findAllForTrending());
    }

}
