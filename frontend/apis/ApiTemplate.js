import axios from "axios";

// axios.defaults.withCredentials = true;

const instance = axios.create({
    baseURL: "https://dev.api.gollaba.net",
    headers: {
        // accessToken: getCookie("accessToken"),
    },
    timeout: 100000,
    // withCredentials: true,
});

const EXPIRED_ACCESS_TOKEN = "액세스 토큰이 만료되었습니다.";

const ApiTemplate = {
    sendApi: async (method, url, body) => {
        let result = null;
        console.log(method, url, body)
        if (body) {
            try {
                result = await instance[method](url, body);
            } catch(e) {
                if (e.response.status === 401 && e.message === EXPIRED_ACCESS_TOKEN) {
                    
                }

                return e.response.data;
            }

            return result.data;
        }

        try {
            result = await instance[method](url);
        } catch(e) {
            if (e.response.status === 401 && e.message === EXPIRED_ACCESS_TOKEN) {
                    
            }
            
            return e.response.data;
        }
        
        return result.data;
    },
}

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

export default ApiTemplate;