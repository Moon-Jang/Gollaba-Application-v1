package kr.mj.gollaba.favorites.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.ErrorAPIResponse;
import kr.mj.gollaba.favorites.dto.CreateFavoritesRequest;
import kr.mj.gollaba.favorites.dto.CreateFavoritesResponse;
import kr.mj.gollaba.favorites.service.FavoritesService;
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
@Api(tags = "Favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @ApiOperation(value = "즐겨찾기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateFavoritesResponse.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @PostMapping(path = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateFavoritesResponse> create(@Validated @RequestBody CreateFavoritesRequest request,
                                                          @ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoritesService.create(request, principalDetails.getUser()));
    }

    @ApiOperation(value = "즐겨찾기 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorAPIResponse.class)))})
    @DeleteMapping(path = "/favorites/{favoritesId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@PathVariable Long favoritesId,
                                          @ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {
        favoritesService.delete(favoritesId, principalDetails.getUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }
}
