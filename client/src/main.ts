import { createApp } from 'vue'
import { router } from "./router";
import './styles/index.css'
import App from './App.vue'
import { initBackendClient } from "./api";
import VueApexCharts from "vue3-apexcharts";
import ECharts from 'vue-echarts'
import { use } from "echarts/core"


// import ECharts modules manually to reduce bundle size
import {CanvasRenderer} from 'echarts/renderers'
import {BarChart} from 'echarts/charts'
import { GridComponent, TooltipComponent } from 'echarts/components'

use([
    CanvasRenderer,
    BarChart,
    GridComponent,
    TooltipComponent
])

initBackendClient();

const app = createApp(App).use(router);
app.use(VueApexCharts);
// register globally (or you can do it locally)
app.component('v-chart', ECharts)
app.mount("#app");
