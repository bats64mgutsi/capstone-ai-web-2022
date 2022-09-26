<template>
    <div v-if="!isProcessing && computedChartData" class="w-11/12 mt-2 mx-auto relative shadow-md sm:rounded-lg">
      <div class="flex flex-row justify-between mt-6">
        <div class="w-1/3">
          <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">AI researchers by rating (Previous Year)</p>
          <apexchart type="pie" :options="computedChartData.pieData.rating_prevYear.chartOptions" :series="computedChartData.pieData.rating_prevYear.series"></apexchart>
        </div>
        <div class="w-1/3">
          <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">AI researchers by rating (Current Year)</p>
          <apexchart type="pie" :options="computedChartData.pieData.rating_currentYear.chartOptions" :series="computedChartData.pieData.rating_currentYear.series"></apexchart>
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

const options = {
  chart: {
    id: 'vuechart-example'
  },
  xaxis: {
    categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998]
  }
};

const donutData = {
  labels: ['Apple', 'Mango', 'Orange', 'Watermelon', 'Fruit 1', 'Fruit 2'],
  series: [30, 40, 45, 50, 49, 60, 70, 91],
}

// const getChartData = () => {
//   return {
//         pieData: {
//             rating_currentYear: {
//                 series: [
//                     23,
//                     32,
//                     43,
//                     9,
//                     2
//                 ],
//                 chartOptions: {
//                     chart: {
//                         width: 200,
//                         type: 'pie',
//                     },
//                     labels: ['A', 'B', 'C', 'Y', 'P'],
//                     responsive: [{
//                         breakpoint: 480,
//                         options: {
//                             chart: {
//                                 width: 200
//                             },
//                             legend: {
//                                 position: 'bottom'
//                             }
//                         }
//                     }]
//                 },
//             },
//             rating_prevYear: {
//                 series: [
//                     23,
//                     32,
//                     43,
//                     9,
//                     2
//                 ],
//                 chartOptions: {
//                     chart: {
//                         width: 200,
//                         type: 'pie',
//                     },
//                     labels: ['A', 'B', 'C', 'P', 'Y'],
//                     responsive: [{
//                         breakpoint: 480,
//                         options: {
//                             chart: {
//                                 width: 200
//                             },
//                             legend: {
//                                 position: 'bottom'
//                             }
//                         }
//                     }]
//                 },
//             }
//         }
//     };
// }

// const data = {
//   series: [44, 55, 13, 43, 22],
//   chartOptions: {
//     chart: {
//       width: 200,
//       type: 'pie',
//     },
//     labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
//     responsive: [{
//       breakpoint: 480,
//       options: {
//         chart: {
//           width: 200
//         },
//         legend: {
//           position: 'bottom'
//         }
//       }
//     }]
//   },
// }
</script>