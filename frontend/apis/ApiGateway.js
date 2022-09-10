import ApiTemplate from './ApiTemplate'
import MethodType from './MethodType'

const ApiGateway = {
    createPoll: async (payload) =>
        ApiTemplate.sendApi(MethodType.POST, '/v1/polls', payload),
    showUser: async (payload) =>
        ApiTemplate.getApi(MethodType.GET, '/v1/users/{userId}', payload),
}

export default ApiGateway
