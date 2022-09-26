<template>
    <div v-if="!isProcessing && computedChartData" class="w-11/12 mt-2 mx-auto relative shadow-md sm:rounded-lg">
      <div class="flex flex-row justify-around mt-6">
        <div class="w-1/3">
          <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">AI researchers by rating (Previous Year)</p>
          <apexchart type="pie" :options="computedChartData.pieData.rating_prevYear.chartOptions" :series="computedChartData.pieData.rating_prevYear.series"></apexchart>
        </div>
        <div class="w-1/3">
          <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">AI researchers by rating (Current Year)</p>
          <apexchart type="pie" :options="computedChartData.pieData.rating_currentYear.chartOptions" :series="computedChartData.pieData.rating_currentYear.series"></apexchart>
        </div>
      </div>
      <div class="flex flex-row justify-around mt-6">
        <div class="w-1/3">
          <apexchart type="bar" height="380" :options="computedChartData.customBarChartData.subfield_previousYear.chartOptions" :series="computedChartData.customBarChartData.subfield_previousYear.series"></apexchart>
        </div>
        <div class="w-1/3">
          <apexchart type="bar" height="380" :options="computedChartData.customBarChartData.subfield_currYear.chartOptions" :series="computedChartData.customBarChartData.subfield_currYear.series"></apexchart>
        </div>
      </div>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, computed } from "vue";
import store from "../../store";
import { refreshData, handleError, getOverallStats, getChartData } from "../util";
import { useRouter } from "vue-router";

const stats = getOverallStats();
const isProcessing = ref<boolean>(false);

const computedChartData = computed(() => {
    return getChartData();
})

onMounted(async () => {
  try {
    isProcessing.value = true;
    await refreshData();
    isProcessing.value = false;
  } catch (e: any) {
    handleError(e);
  }
});
</script>