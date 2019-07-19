import Servers from './Servers'
import {MediaType} from '../js/Server'

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

class PersonApi {
    #server

    constructor(server) {
        this.#server = server
    }

    get() {
        return this.#server.get({
            path: "/person/get",
            contentType: MediaType.PROTOBUF,
        })
    }

    updateAndGet(person) {
        return this.#server.put({
            path: "/person/update-and-get",
            data: person,
            contentType: MediaType.PROTOBUF,
        })
    }
}