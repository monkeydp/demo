import {Server} from '../js/Server'

export default {
    install(Vue, config) {
        Vue.prototype.$server = new Server(config)
    }
}