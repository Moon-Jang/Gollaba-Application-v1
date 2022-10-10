import ApiTemplate from "./ApiTemplate"
import MethodType from "./MethodType"

const ApiGateway = {
    createPoll: async (payload, token) => ApiTemplate.sendApiMultiPart(MethodType.POST, "/v1/polls", payload, token),
    getPolls: async (offset, limit) =>
        ApiTemplate.sendApi(MethodType.GET, `/v1/polls?limit=${limit}&offset=${offset * 15}`),
    getPoll: async pollId => ApiTemplate.sendApi(MethodType.GET, `/v1/polls/${pollId}`),
    vote: async payload => ApiTemplate.sendApi(MethodType.POST, "/v1/vote", payload),
    showUser: async (userId, token) => ApiTemplate.sendApi(MethodType.GET, `/v1/users/${userId}`, null, token),
    updateForm: async (formData, token) =>
        ApiTemplate.sendApiMultiPart(MethodType.POST, `v1/users/update`, formData, token),
    signupForm: async formData => ApiTemplate.sendApiMultiPart(MethodType.POST, `v1/signup`, formData),
}

export default ApiGateway
