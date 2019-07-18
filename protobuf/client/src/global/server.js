import Axios from 'axios'
import ProtoRoot from '../generated/js/proto'

let axios = Axios.create({
    headers: {
        'Content-Type': 'application/x-protobuf'
    },
    responseType: 'arraybuffer'
})

let serverAddress = 'http://localhost:8080/';

function loadMessageClass(messageClassName) {
    let arr = messageClassName.split('.')
    let messageClass = ProtoRoot
    arr.forEach(item => {
        messageClass = messageClass[item]
    })
    return messageClass
}

function parseData(resp) {
    let contentType = resp.headers['content-type']
    switch (contentType) {
        case 'application/json':
            return resp.data
            break
        case 'application/x-protobuf':
            return parseProtobufData(resp)
            break
        default:
            throw Error('未处理响应内容类型: ' + contentType)
    }
}

function parseProtobufData(resp) {
    let buffer = resp.data
    let messageClassName = resp.headers['x-protobuf-message']
    if (messageClassName == null || messageClassName == '') {
        throw Error('MessageClassName 不能为空!')
    }
    let messageClass = loadMessageClass(messageClassName)
    if (buffer instanceof ArrayBuffer) {
        let uint8Array = new Uint8Array(buffer)
        return messageClass.decode(uint8Array)
    }
    throw Error('未处理数据类型: ' + buffer.constructor.name)
}

export default {
    install(Vue) {
        Vue.prototype.$server = {
            get: function (path) {
                return this.request("get", path, null);
            },
            post: function (path, data) {
                return this.request("post", path, data);
            },
            put: function (path, data) {
                return this.request("put", path, data);
            },
            request: function (method, path, data = null) {
                let url = serverAddress + path;
                return axios.request({
                    url: url,
                    method: method,
                    data: data,
                }).then(resp => {
                    let data = parseData(resp)
                    return data
                });
            },
        }
    }
}