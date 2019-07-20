import Axios from 'axios'
import _ from 'lodash'

class Method {
    static GET = 'get'
    static POST = 'post'
    static PUT = 'put'
    static DELETE = 'delete'
}

export class MediaType {
    static JSON = 'application/json'
    static PROTOBUF = 'application/x-protobuf'
}

export class ProtocolType {
    static HTTP = 'http'
    static HTTPS = 'https'
}

export class Server {
    #DEFAULT_CONFIG = {
        request: {
            contentType: MediaType.JSON,
            protocolType: ProtocolType.HTTP,
        }
    }

    #config
    #serverConfig
    #dataEncoder
    #dataDecoder
    #axios

    constructor(
        config = {
            base: {
                host: null,
                port: null,
            },
            dataDecoder: {
                protoRoot: null,
            },
            default: {}
        }) {
        config.default = _.merge(config.default, this.#DEFAULT_CONFIG)
        this.#config = config

        this.#serverConfig = new ServerBaseConfig(this.#config.base)
        this.#dataEncoder = new DataEncoder()
        this.#dataDecoder = new DataDecoder(this.#config.dataDecoder)
        this.#axios = Axios.create()
    }

    get(reqConfig) {
        let coverReqConfig = {method: Method.GET}
        return this.request(_.merge({}, reqConfig, coverReqConfig));
    }

    post(reqConfig) {
        let coverReqConfig = {method: Method.POST}
        return this.request(_.merge({}, reqConfig, coverReqConfig));
    }

    put(reqConfig) {
        let coverReqConfig = {method: Method.PUT}
        return this.request(_.merge({}, reqConfig, coverReqConfig));
    }

    request(reqConfig) {
        let serverReqConfig = new ServerRequestConfig(_.merge({}, this.#config.default.request, reqConfig))
        let axiosReqConfig = this._buildAxiosRequestConfig(serverReqConfig)
        return this.#axios.request(axiosReqConfig).then(resp => {
            let data = this.#dataDecoder.decodeData(resp)
            return data
        });
    }

    _buildAxiosRequestConfig(serverReqConfig) {
        let contentType = serverReqConfig.contentType
        let axiosReqConfig = {
            url: this._url(serverReqConfig),
            method: serverReqConfig.method,
            data: this.#dataEncoder.encodeData(contentType, serverReqConfig.data),
        }
        let axiosDefaultReqConfig = AxiosDefaultRequestConfig.getByMediaType(contentType)
        return _.merge({}, axiosDefaultReqConfig, axiosReqConfig)
    }

    _url(serverReqConfig) {
        return serverReqConfig.protocolType + "://"
            + this.#serverConfig.host + ":" + this.#serverConfig.port + "/"
            + serverReqConfig.path
    }
}

class AxiosDefaultRequestConfig {
    static #map = {
        [MediaType.JSON]: {
            headers: {
                'Content-Type': MediaType.JSON
            },
            withCredentials: true,
        },
        [MediaType.PROTOBUF]: {
            headers: {
                'Content-Type': MediaType.PROTOBUF
            },
            responseType: 'arraybuffer',
            withCredentials: true,
        }
    }

    static getByMediaType(mediaType) {
        return this.#map[mediaType]
    }
}

class DataEncoder {
    encodeData(mediaType, data) {
        if (data == null) return null
        switch (mediaType) {
            case MediaType.JSON:
                return this._encodeJsonData(data)
            case MediaType.PROTOBUF:
                return this._encodeProtobufData(data)
            default:
                throw new Error('Unprocessed request content type: ' + mediaType)
        }
    }

    _encodeJsonData(data) {
        return data
    }

    _encodeProtobufData(data) {
        let unit8Array = data.constructor.encode(data).finish()
        let arrayBuffer = unit8Array.slice().buffer
        return arrayBuffer
    }
}

class DataDecoder {
    #protoRoot

    constructor(config) {
        this.#protoRoot = config.protoRoot
    }

    decodeData(resp) {
        let respContentType = resp.headers['content-type']
        if (StringUtil.isEmpty(respContentType)) {
            return resp.data
        }
        switch (respContentType) {
            case MediaType.JSON:
                return this._decodeJsonData(resp)
            case MediaType.PROTOBUF:
                return this._decodeProtobufData(resp)
            default:
                throw new Error('Unprocessed response content type: ' + respContentType)
        }
    }

    _decodeJsonData(resp) {
        return resp.data
    }

    _decodeProtobufData(resp) {
        let buffer = resp.data
        let messageClassName = resp.headers['x-protobuf-message']
        if (messageClassName == null || messageClassName == '') {
            throw new Error('MessageClassName must not be empty!')
        }
        let messageClass = this._loadMessageClass(messageClassName)
        if (buffer instanceof ArrayBuffer) {
            let uint8Array = new Uint8Array(buffer)
            return messageClass.decode(uint8Array)
        }
        throw new Error('Unprocessed response data type: ' + buffer.constructor.name)
    }

    _loadMessageClass(messageClassName) {
        let arr = messageClassName.split('.')
        let messageClass = this.#protoRoot
        arr.forEach(item => {
            messageClass = messageClass[item]
        })
        return messageClass
    }
}

class ServerBaseConfig {
    name
    host
    port

    constructor(config) {
        this.name = config.name
        this.host = config.host
        this.port = config.port
    }
}

class ServerRequestConfig {
    constructor(config) {
        this.path = config.path
        this.method = config.method
        this.data = config.data
        this.contentType = config.contentType
        this.protocolType = config.protocolType
    }
}