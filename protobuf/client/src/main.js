import Vue from 'vue'
import App from './App.vue'
import Server from './global/server';

Vue.use(Server)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app')
