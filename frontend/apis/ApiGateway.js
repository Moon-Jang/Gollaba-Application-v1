import ApiTemplate from "./ApiTemplate";
import MethodType from "./MethodType";

const ApiGateway = {
    createPoll: async (payload) => ApiTemplate.sendApi(MethodType.POST, "/v1/polls", payload),
}

export default ApiGateway;