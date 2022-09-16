<template>
  <div class="w-11/12 mt-2 mx-auto relative shadow-md sm:rounded-lg">
    <div class="flex flex-row justify-between mt-6">
      <div class="w-1/3">
        <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">Current rated AI researchers by institution</p>
        <apexchart type="bar" :options="options" :series="series"></apexchart>
      </div>
      <div class="w-1/3">
        <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">Current rated AI researchers by rating</p>
        <apexchart type="line" :options="options" :series="series"></apexchart>
      </div>
      <div class="w-1/3">
        <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">Current rated AI researchers by specialization</p>
        <apexchart type="donut" :options="{}" :series="donutSeries"></apexchart>
      </div>
    </div>

    <div class="flex flex-row justify-between mt-7">
      <div class="w-1/3">
        <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">Current rated AI researchers by institution</p>
        <apexchart type="bar" :options="options" :series="series"></apexchart>
      </div>
      <div class="w-1/3">
        <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">Current rated AI researchers by rating</p>
        <apexchart type="line" :options="options" :series="series"></apexchart>
      </div>
      <div class="w-1/3">
        <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">Current rated AI researchers by per institution</p>
        <apexchart type="donut" :options="{}" :series="donutSeries"></apexchart>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, watch } from "vue";
import store from "../../store";
import { refreshData } from "../util";
import { useRouter } from "vue-router";
const data = ref<Array<any>>([]);
const options = {
chart: {
  id: 'vuechart-example'
},
xaxis: {
  categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998]
}
};
const series = [{
name: 'series-1',
data: [30, 40, 45, 50, 49, 60, 70, 91]
}]
const donutSeries = [30, 40, 45, 50, 49, 60, 70, 91];
</script>