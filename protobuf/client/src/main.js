import Vue from 'vue'
import App from './App.vue'
import Server from './global/server';
import VueLodash from 'vue-lodash'
import ProtoRoot from './generated/js/proto'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

let config = {
    server: {
        name: 'myserver',
        host: 'localhost',
        port: '8080',
    },
    dataDecoder: {
        protoRoot: ProtoRoot,
    }
}

Vue.use(Server, config)
Vue.use(VueLodash)
Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app')
