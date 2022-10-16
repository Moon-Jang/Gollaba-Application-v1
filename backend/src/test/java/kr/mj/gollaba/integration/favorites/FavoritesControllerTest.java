package kr.mj.gollaba.integration.favorites;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.service.HashIdService;
import kr.mj.gollaba.common.util.JsonStringifyUtils;
import kr.mj.gollaba.favorites.repository.FavoritesRepository;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FavoritesControllerTest extends IntegrationTest {

	@Autowired
	private FavoritesRepository favoritesRepository;

	@Autowired
	private HashIdService hashIdService;

	@DisplayName("즐겨찾기 생성")
	@WithUserDetails(value = UserFactory.TEST_EXIST_UNIQUE_ID)
	@Test
	public void create() throws Exception {
		//given
		final long pollId = 1L;
		String hashId = hashIdService.encode(pollId);
		Map<String, String> requestBody = Map.of(
				"pollId", hashId
		);

		//when
		ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/favorites")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonStringifyUtils.stringify(requestBody))
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andDo(print())
				.andExpect(status().isCreated());
	}
}
