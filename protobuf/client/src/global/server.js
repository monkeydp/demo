import Axios from 'axios'
import ProtoRoot from '../generated/js/proto'

let mediaType = {
    protobuf: 'application/x-protobuf',
    json: 'application/json',
}

let defaultAxiosRequestConfig = {
    headers: {
        'Content-Type': mediaType.protobuf
    },
    responseType: 'arraybuffer'
}

let axios = Axios.create(defaultAxiosRequestConfig)

let serverAddress = 'http://localhost:8080/';

function encodeData(reqContentType, data) {
    if (data == null) return null
    switch (reqContentType) {
        case mediaType.json:
            return encodeJsonData(data)
            break
        case mediaType.protobuf:
            return encodeProtobufData(data)
            break
        default:
            throw Error('Unprocessed request content type: ' + contentType)
    }
}

function encodeJsonData(data) {
    return data
}

function encodeProtobufData(data) {
    let unit8Array = data.constructor.encode(data).finish()
    let arrayBuffer = unit8Array.slice().buffer
    return arrayBuffer
}

function loadMessageClass(messageClassName) {
    let arr = messageClassName.split('.')
    let messageClass = ProtoRoot
    arr.forEach(item => {
        messageClass = messageClass[item]
    })
    return messageClass
}

function decodeData(resp) {
    let respContentType = resp.headers['content-type']
    switch (respContentType) {
        case mediaType.json:
            return decodeJsonData(resp)
            break
        case mediaType.protobuf:
            return decodeProtobufData(resp)
            break
        default:
            throw Error('Unprocessed response content type: ' + respContentType)
    }
}

function decodeJsonData(resp) {
    return resp.data
}

function decodeProtobufData(resp) {
    let buffer = resp.data
    let messageClassName = resp.headers['x-protobuf-message']
    if (messageClassName == null || messageClassName == '') {
        throw Error('MessageClassName must not be empty!')
    }
    let messageClass = loadMessageClass(messageClassName)
    if (buffer instanceof ArrayBuffer) {
        let uint8Array = new Uint8Array(buffer)
        return messageClass.decode(uint8Array)
    }
    throw Error('Unprocessed response data type: ' + buffer.constructor.name)
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
                let reqContentType = axios.defaults.headers['Content-Type']
                return axios.request({
                    url: url,
                    method: method,
                    data: encodeData(reqContentType, data),
                }).then(resp => {
                    let data = decodeData(resp)
                    return data
                });
            },
        }
    }
}