import ApiTemplate from "./ApiTemplate"
import MethodType from "./MethodType"

const ApiGateway = {
    createPoll: async (payload, token) => ApiTemplate.sendApiMultiPart(MethodType.POST, "/v1/polls", payload, token),
    getPolls: async (offset, limit) =>
        ApiTemplate.sendApi(MethodType.GET, `/v1/polls?limit=${limit}&offset=${offset * 15}`),
    getPoll: async pollId => ApiTemplate.sendApi(MethodType.GET, `/v1/polls/${pollId}`),
    vote: async payload => ApiTemplate.sendApi(MethodType.POST, "/v1/vote", payload),
    update: async (pollId, payload, token) =>
        ApiTemplate.sendApiMultiPart(MethodType.POST, `/v1/polls/${pollId}/update`, payload, token),
}

export default ApiGateway
