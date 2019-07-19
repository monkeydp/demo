import Axios from 'axios'
import ProtoRoot from '../generated/js/proto'

let Method = {
    GET: 'get',
    POST: 'post',
    PUT: 'put',
    DELETE: 'delete',
}

let MediaType = {
    JSON: 'application/json',
    PROTOBUF: 'application/x-protobuf',
}

let ProtocolType = {
    HTTP: 'http',
    HTTPS: 'https',
    WS: 'ws',
    WSS: 'wss',
}

let axiosJsonRequestConfig = {
    headers: {
        'Content-Type': MediaType.JSON
    }
}
let axiosProtobufRequestConfig = {
    headers: {
        'Content-Type': MediaType.PROTOBUF
    },
    responseType: 'arraybuffer'
}

let axios = Axios.create()

let serverAddress = 'http://localhost:8080/';

function url(path) {
    return serverAddress + path
}

function axiosRequestConfig(serverReqConfig) {
    let contentType = serverReqConfig.contentType
    let axiosReqConfig = {
        url: url(serverReqConfig.path),
        method: serverReqConfig.method,
        data: encodeData(contentType, serverReqConfig.data),
    }
    let defaultAxiosReqConfig = getDefaultAxiosRequestConfig(contentType)
    return _.merge({}, defaultAxiosReqConfig, axiosReqConfig)
}

function getDefaultAxiosRequestConfig(mediaType) {
    switch (mediaType) {
        case MediaType.PROTOBUF:
            return axiosProtobufRequestConfig
            break
        case MediaType.JSON:
        default:
            return axiosJsonRequestConfig
            break
    }
}

function encodeData(mediaType, data) {
    if (data == null) return null
    switch (mediaType) {
        case MediaType.JSON:
            return encodeJsonData(data)
            break
        case MediaType.PROTOBUF:
            return encodeProtobufData(data)
            break
        default:
            throw new Error('Unprocessed request content type: ' + mediaType)
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
        case MediaType.JSON:
            return decodeJsonData(resp)
            break
        case MediaType.PROTOBUF:
            return decodeProtobufData(resp)
            break
        default:
            throw new Error('Unprocessed response content type: ' + respContentType)
    }
}

function decodeJsonData(resp) {
    return resp.data
}

function decodeProtobufData(resp) {
    let buffer = resp.data
    let messageClassName = resp.headers['x-protobuf-message']
    if (messageClassName == null || messageClassName == '') {
        throw new Error('MessageClassName must not be empty!')
    }
    let messageClass = loadMessageClass(messageClassName)
    if (buffer instanceof ArrayBuffer) {
        let uint8Array = new Uint8Array(buffer)
        return messageClass.decode(uint8Array)
    }
    throw new Error('Unprocessed response data type: ' + buffer.constructor.name)
}

class ServerRequestConfig {
    constructor(config) {
        this.path = config.path
        this.method = config.method
        this.data = config.data
        this.contentType = config.contentType ? config.contentType : MediaType.JSON
        this.protocolType = config.protocolType ? config.protocolType : ProtocolType.HTTP
    }
}

export default {
    install(Vue) {
        Vue.prototype.$server = {
            MediaType: MediaType,
            ProtocolType: ProtocolType,
            get: function (config) {
                let coverConfig = {method: Method.GET}
                return this.request(_.merge({}, config, coverConfig));
            },
            post: function (config) {
                let coverConfig = {method: Method.POST}
                return this.request(_.merge({}, config, coverConfig));
            },
            put: function (config) {
                let coverConfig = {method: Method.PUT}
                return this.request(_.merge({}, config, coverConfig));
            },
            request: function (config) {
                let serverReqConfig = new ServerRequestConfig(config)
                let axiosReqConfig = axiosRequestConfig(serverReqConfig)
                return axios.request(axiosReqConfig).then(resp => {
                    let data = decodeData(resp)
                    return data
                });
            },
        }
    }
}