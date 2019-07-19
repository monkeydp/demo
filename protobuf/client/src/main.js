import Vue from 'vue'
import App from './App.vue'
import Api from './global/api';
import VueLodash from 'vue-lodash'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(Api)
Vue.use(VueLodash)
Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app')
