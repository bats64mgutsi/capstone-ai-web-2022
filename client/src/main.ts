import { createApp } from 'vue'
import { router } from "./router";
import './styles/index.css'
import App from './App.vue'
import { initBackendClient } from "./api";
import VueApexCharts from "vue3-apexcharts";

initBackendClient();

const app = createApp(App).use(router);
app.use(VueApexCharts);
app.mount("#app");
