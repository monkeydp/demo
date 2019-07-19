import Vue from 'vue'
import App from './App.vue'
import Server from './global/server';
import VueLodash from 'vue-lodash'
import ProtoRoot from './generated/js/proto'

let config = {
    server: {
        host: 'localhost',
        port: '8080',
    },
    dataDecoder: {
        protoRoot: ProtoRoot,
    }
}

Vue.use(Server, config)
Vue.use(VueLodash)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app')
