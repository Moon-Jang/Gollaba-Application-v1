import ApiTemplate from "./ApiTemplate"
import MethodType from "./MethodType"

const ApiGateway = {
    //투표
    createPoll: async (payload, token) => ApiTemplate.sendApiMultiPart(MethodType.POST, "/v1/polls", payload, token),
    getPolls: async (offset, limit, token) =>
        ApiTemplate.sendApi(MethodType.GET, `/v1/polls?limit=${limit}&offset=${offset * 15}`, null, token),
    getPoll: async pollId => ApiTemplate.sendApi(MethodType.GET, `/v1/polls/${pollId}`),
    vote: async (pollId, payload) => ApiTemplate.sendApi(MethodType.POST, `/v1/polls/${pollId}/vote`, payload),
    updatePoll: async (pollId, payload, token) =>
        ApiTemplate.sendApi(MethodType.POST, `v1/polls/${pollId}/update`, payload, token),

    // 회원가입
    signupForm: async formData => ApiTemplate.sendApiMultiPart(MethodType.POST, `v1/signup`, formData),
    // 회원조회
    showUser: async (userId, token) => ApiTemplate.sendApi(MethodType.GET, `/v1/users/${userId}`, null, token),
    // 회원수정
    updateForm: async (formData, token) =>
        ApiTemplate.sendApiMultiPart(MethodType.POST, `v1/users/update`, formData, token),
    //즐겨찾기
    makeFavorite: async (payload, token) => ApiTemplate.sendApi(MethodType.POST, `v1/favorites`, payload, token),
    deleteFavorite: async (favoritesId, token) =>
        ApiTemplate.sendApi(MethodType.DELETE, `v1/favorites/${favoritesId}`, null, token),
    getFavorites: async token => ApiTemplate.sendApi(MethodType.GET, `v1/favorites`, null, token),
}

export default ApiGateway
