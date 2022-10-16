package kr.mj.gollaba.poll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.ErrorAPIResponse;
import kr.mj.gollaba.common.aspect.ParseHashId;
import kr.mj.gollaba.common.util.HttpRequestUtils;
import kr.mj.gollaba.poll.dto.*;
import kr.mj.gollaba.poll.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(Const.ROOT_URL)
@Api(tags = "Poll")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @ApiOperation(value = "투표 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(implementation = CreatePollResponse.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @PostMapping(path = "/polls", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatePollResponse> create(@Validated CreatePollRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pollService.create(request));
    }

    @ApiOperation(value = "투표 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FindAllPollResponse.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @GetMapping(path = "/polls")
    public ResponseEntity<FindAllPollResponse> findAll(@Validated FindAllPollRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.findAll(request));
    }

    @ParseHashId
    @ApiOperation(value = "투표 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FindPollResponse.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @GetMapping(path = "/polls/{pollId}")
    public ResponseEntity<FindPollResponse> find(@ApiParam(type = "string") @PathVariable Object pollId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.find((Long) pollId));
    }


    @ParseHashId
    @ApiOperation(value = "투표 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @PostMapping(path = "/polls/{pollId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> update(@PathVariable Object pollId,
                                           @Validated UpdatePollRequest request,
                                           @ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {
        request.validate();
        pollService.update((Long) pollId, request, principalDetails.getUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }

    @ParseHashId
    @ApiOperation(value = "투표하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @PostMapping(path = "/polls/{pollId}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> vote(@PathVariable Object pollId,
                                        @Validated @RequestBody VoteRequest request) {
        request.setIpAddress(HttpRequestUtils.getClientIpAddress());

        pollService.vote((Long) pollId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }

}
