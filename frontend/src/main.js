
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

/**
 * [ Vuetify ]
*
* npm install vuetify
* https://vuetifyjs.com/en/getting-started/installation/#existing-projects
*/
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

/**
 * [ Material - Icon ]
 *
 * npm install @mdi/font
 * npm install material-design-icons-iconfont
 *
 * https://vuetifyjs.com/en/features/icon-fonts/#material-icons
 */
import '@mdi/font/css/materialdesignicons.css'
import { aliases, mdi } from "vuetify/iconsets/mdi";

const app = createApp(App)

const vuetify = createVuetify({
    components,
    directives,
    icons:{
        defaultSet: 'mdi',
        aliases,
        sets: {
            mdi
        },
    }
})

/**
 * [ axios ]
 * npm install axios
 * 
 * https://axios-http.com/kr/docs/intro
 */
import {instance} from "@/api/axios"

import VueCookies from 'vue3-cookies'
app.use(VueCookies, {
    expireTimes: "1d",
});

app.provide('axios', instance)
app.use(createPinia())
app.use(router)
app.use(vuetify)
app.mount('#app')
