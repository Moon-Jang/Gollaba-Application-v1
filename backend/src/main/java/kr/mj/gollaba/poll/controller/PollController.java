package kr.mj.gollaba.poll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.ErrorAPIResponse;
import kr.mj.gollaba.poll.dto.CreatePollRequest;
import kr.mj.gollaba.poll.dto.FindAllPollRequest;
import kr.mj.gollaba.poll.dto.FindAllPollResponse;
import kr.mj.gollaba.poll.service.PollService;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Const.ROOT_URL)
@Api(tags = "Poll")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @ApiOperation(value = "투표 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @PostMapping(path = "/polls", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> create(@Validated @RequestBody CreatePollRequest request) {
        pollService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }

    @ApiOperation(value = "투표 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FindAllPollResponse.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @GetMapping(path = "/polls", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllPollResponse> findAll(@Validated FindAllPollRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.findAll(request));
    }

}
