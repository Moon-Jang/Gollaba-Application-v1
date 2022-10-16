package kr.mj.gollaba.integration.favorites;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.service.HashIdService;
import kr.mj.gollaba.common.util.JsonStringifyUtils;
import kr.mj.gollaba.favorites.entity.Favorites;
import kr.mj.gollaba.favorites.repository.FavoritesRepository;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static kr.mj.gollaba.unit.user.factory.UserFactory.TEST_EXIST_UNIQUE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FavoritesControllerTest extends IntegrationTest {

	@Autowired
	private FavoritesRepository favoritesRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PollRepository pollRepository;

	@Autowired
	private HashIdService hashIdService;

	@DisplayName("즐겨찾기 생성")
	@WithUserDetails(value = TEST_EXIST_UNIQUE_ID)
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

	@DisplayName("즐겨찾기 삭제")
	@WithUserDetails(value = TEST_EXIST_UNIQUE_ID)
	@Test
	public void delete() throws Exception {
		//given
		final long pollId = 1L;
		User user = userRepository.findByUniqueId(TEST_EXIST_UNIQUE_ID)
				.orElseThrow(() -> new IllegalArgumentException());
		Poll poll = pollRepository.findById(pollId)
				.orElseThrow(() -> new IllegalArgumentException());
		Favorites favorites = favoritesRepository.save(Favorites.of(user, poll));
		String url = Const.ROOT_URL + "/favorites/" + favorites.getId();

		//when
		ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andDo(print())
				.andExpect(status().isOk());

		Favorites result = favoritesRepository.findById(favorites.getId())
				.orElse(null);

		assertThat(result).isNull();
	}
}
