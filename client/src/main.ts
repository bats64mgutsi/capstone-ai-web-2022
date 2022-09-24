import { createApp } from 'vue'
import { router } from "./router";
import './styles/index.css'
import App from './App.vue'
import { initBackendClient } from "./api";
import VueApexCharts from "vue3-apexcharts";
import { registerToastManager } from "./app-utils";

initBackendClient();

const app = createApp(App).use(router);
app.use(VueApexCharts);
registerToastManager(app);
app.mount("#app");
