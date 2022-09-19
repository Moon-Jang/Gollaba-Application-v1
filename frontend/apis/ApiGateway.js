import ApiTemplate from './ApiTemplate'
import MethodType from './MethodType'

const ApiGateway = {
    createPoll: async (payload) =>
        ApiTemplate.sendApi(MethodType.POST, '/v1/polls', payload),
    showUser: async (userId, token) =>
        ApiTemplate.sendApi(MethodType.GET, `/v1/users/${userId}`, null, token),
    updateNickName: async (formData, token) =>
        ApiTemplate.sendApiMultiPart(
            MethodType.POST,
            `/v1​/users​/update`,
            formData,
            token
        ),
}

export default ApiGateway
