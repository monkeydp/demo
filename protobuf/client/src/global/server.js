import Axios from 'axios'

let axios = Axios.create({
    headers: {
        'Content-Type': 'application/x-protobuf'
    },
    responseType: 'arraybuffer'
})

let serverAddress = 'http://localhost:8080/';

export default {
    install(Vue, options) {
        Vue.prototype.$server = {
            get: function (path, responseDataType = null) {
                return this.request("get", path, null, responseDataType);
            },
            post: function (path, data, responseDataType = null) {
                return this.request("post", path, data, responseDataType);
            },
            put: function (path, data, responseDataType = null) {
                return this.request("put", path, data, responseDataType);
            },
            request: function (method, path, data = null, responseDataType = null) {
                let url = serverAddress + path;
                return axios.request({
                    url: url,
                    method: method,
                    data: data,
                }).then(resp => {
                    let data = resp.data;
                    if (responseDataType == null) {
                        return data;
                    }
                    if (data instanceof ArrayBuffer) {
                        let uint8Array = new Uint8Array(data)
                        return responseDataType.decode(uint8Array)
                    }
                    throw Error('未处理数据类型: ' + data.constructor.name)
                });
            },
        }
    }
}