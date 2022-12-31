package kr.mj.gollaba.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.ErrorAPIResponse;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.dto.FindUserResponse;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.dto.UpdateUserRequest;
import kr.mj.gollaba.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(Const.ROOT_URL)
@Api(tags = "User")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@ApiOperation(value = "회원 가입")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
					schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorAPIResponse.class)))})
	@PostMapping(path = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Boolean> signup(@Validated SignupRequest request) {
		request.validate();
		userService.create(request);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(true);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "회원 조회", authorizations = { @Authorization(value = AUTHORIZATION)})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = FindUserResponse.class))),
			@ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorAPIResponse.class)))})
	@GetMapping(path = "/users/{userId}")
	public ResponseEntity<FindUserResponse> find(@PathVariable Long userId, @ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userService.find(userId, principalDetails.getUser()));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "회원 수정")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
					schema = @Schema(implementation = SignupResponse.class))),
			@ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorAPIResponse.class)))})
	@PostMapping(path = "/users/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Boolean> update(@Validated UpdateUserRequest request, @ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {
		request.validate();

		switch (request.getUpdateType()) {
			case NICKNAME:
				userService.updateNickName(request, principalDetails.getUser());
				break;
			case PASSWORD:
				userService.updatePassword(request, principalDetails.getUser());
				break;
			case PROFILE_IMAGE:
				userService.updateProfileImage(request, principalDetails.getUser());
				break;
			case BACKGROUND_IMAGE:
				userService.updateBackgroundImage(request, principalDetails.getUser());
				break;
			default:
				throw new GollabaException(GollabaErrorCode.INVALID_PARAMS);
		}

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(true);
	}
}
