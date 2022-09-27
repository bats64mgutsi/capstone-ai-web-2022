<template>
    <div v-if="!isProcessing && stats && computedChartData" class="w-11/12 mt-2 mx-auto relative shadow-md sm:rounded-lg">
      <div class="flex flex-row justify-around mt-6 mb-8">
        <div class="">
          <h1 class="mx-auto font-semibold text-gray-900 dark:text-white mb-4">Total Number of AI-related Citations (Current Year)</h1>
          <div class="rounded-full w-36 h-36 flex items-center justify-center mx-auto bg-cyan-500 text-white">{{stats.noCitationsPreviousYear}}</div>
        </div>
        <div class="">
          <h1 class="mx-auto font-semibold text-gray-900 dark:text-white mb-4">Change compared to previous year</h1>
          <div class="rounded-full w-36 h-36 flex items-center justify-center m-auto bg-cyan-500 text-white">{{`${computeChange(stats.noCitationsPreviousYear, stats.noCitationsCurrentYear)}%`}}</div>
        </div>
      </div>
      <div class="flex flex-row justify-around">
        <div class="mt-10">
          <h1 class="mx-auto font-semibold text-gray-900 dark:text-white mb-4">Total Number of AI-related Publications (Current Year)</h1>
          <div class="rounded-full w-36 h-36 flex items-center justify-center mx-auto bg-cyan-500 text-white">{{stats.noPublicationsPreviousYear}}</div>
        </div>
        <div class="mt-10 mb-10">
          <h1 class="mx-auto font-semibold text-gray-900 dark:text-white mb-4">Change compared to previous year</h1>
          <div class="rounded-full w-36 h-36 flex items-center justify-center m-auto bg-cyan-500 text-white">{{`${computeChange(stats.noPublicationsPreviousYear, stats.noPublicationsCurrentYear)}%`}}</div>
        </div>
      </div>
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

const stats = store.overallStats;
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

const computeChange = (prevYear: number, currentYear: number) => {
    const denom = prevYear + currentYear;
    if(denom == 0) return 0;

    const change = currentYear - prevYear;
    return (change/denom).toFixed(2);
}

const isNegative = (value: number) => {
    return value < 0;
}
</script>