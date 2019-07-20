import Servers from './Servers'
import {MediaType} from '../tool/Server'
import StringUtil from '../tool/StringUtil'

export default {
    install(Vue) {
        if (Vue.prototype.$api == null) {
            Vue.prototype.$api = {}
        }
        registerAllApis(Vue.prototype.$api)
    }
}

function registerAllApis(api) {
    api['person'] = new PersonApi(Servers.SERVER_A)
}

class BaseApi {
    pathPrefix = null
    #pathSeparator = '/'

    _path(simplePath) {
        let pathArray = []
        if (!StringUtil.isEmpty(this.pathPrefix)) {
            pathArray.push(this.pathPrefix)
        }
        pathArray.push(simplePath)
        return this.#pathSeparator + pathArray.join(this.#pathSeparator)
    }
}

class PersonApi extends BaseApi {
    #server
    pathPrefix = 'person'

    constructor(server) {
        super()
        this.#server = server
    }

    get() {
        return this.#server.get({
            path: super._path('get'),
            contentType: MediaType.PROTOBUF,
        })
    }

    updateAndGet(person) {
        return this.#server.put({
            path: super._path('update-and-get'),
            data: person,
            contentType: MediaType.PROTOBUF,
        })
    }
}