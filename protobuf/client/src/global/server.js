import {Server} from '../js/Server'

export default {
    install(Vue, config) {
        if (Vue.prototype.$server == null) {
            Vue.prototype.$server = {}
        }
        Vue.prototype.$server[config.server.name] = new Server(config);
    }
}