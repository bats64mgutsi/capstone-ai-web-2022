import { createApp } from 'vue'
import { router } from "./router";
import './styles/index.css'
import App from './App.vue'
import { initBackendClient } from "./rpc";

initBackendClient();

const app = createApp(App).use(router)
app.mount("#app")
