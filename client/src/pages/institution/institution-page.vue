<template>  
    <div v-if="!isProcessing && computedChartData" class="w-11/12 mt-2 mx-auto overflow-x-auto relative shadow-md sm:rounded-lg mb-6">
        <div class="w">
          <p class="text-center mb-2 font-semibold text-gray-900 dark:text-white">AI researchers by institution</p>
          <apexchart type="bar" height="600" :options="computedChartData.chartOptions" :series="computedChartData.series" @click="onChartClick" ></apexchart>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, computed } from "vue";
import store from "../../store";
import { refreshData, handleError, getInstitutionChartData } from "../util";
import { useRouter } from "vue-router";
import { InstitutionStat } from "../../core";

const stats = store.institutionStats;
const router = useRouter();
const isProcessing = ref<boolean>(false);
const searchInput = ref<string>('');
const searchedContent = ref<Array<InstitutionStat>>([]);
const pageContent = ref<Array<InstitutionStat>>([]);
const page = ref<number>(1);

const computedChartData = computed(() => {
    return getInstitutionChartData();
})

onMounted(async () => {
    try {
        isProcessing.value = true;
        await refreshData();
        initLocalVars();
        isProcessing.value = false;
    } catch (e) {
        handleError(e);
    }
});

const initLocalVars = () => {
    setSearchedContent();
    setPageContent();
}

const setSearchedContent = () => {
    searchedContent.value = stats.value.length ? JSON.parse(JSON.stringify(stats.value)) : [];
}

const setPageContent = () => {
    pageContent.value = searchedContent.value.slice(((page.value - 1) * 10), getValidEndIndex(searchedContent.value, ((page.value - 1) * 10) + 10));
}

const getValidEndIndex = (content: Array<InstitutionStat>, endIndex: number) => {
    return (endIndex >= content.length) ? (content.length + 1) : endIndex;
}

const handlePreviousBtnClickEvent = () => {
    page.value = ((page.value - 1) <= 0) ? page.value : (isValidPageNumber(page.value - 1) ? (page.value - 1) : page.value);
    setPageContent();
}

const handleNextBtnClickEvent = () => {
    page.value = isValidPageNumber(page.value + 1) ? (page.value + 1) : page.value;
    setPageContent();
}

const isValidPageNumber = (pageNumber: number) => {
    return getFirstRecordNumberForGivenPage(pageNumber) < searchedContent.value.length;
}

const getFirstRecordNumberForGivenPage = (page: number) => {
    return ((page * 10) - 10) + 1;
}

const getLastRecordNumberForGivenPage = (page: number) => {
    return (page * 10) > searchedContent.value.length ? searchedContent.value.length : (page * 10);
}

const goToAuthorsPage = (field: string, value: string) => {
    router.push({ name: "authors", params: { field, value } });
}

const handleSearchInput = () => {
    isProcessing.value = true;
    
    setSearchedContent();

    if (searchInput.value.length) {
        searchedContent.value = getContentWithFiltersApplied(searchedContent.value, [searchInput.value]);
    }

    resetPage();
    setPageContent();

    isProcessing.value = false;
}

const resetPage = () => {
    page.value = 1;
}

const getContentWithFiltersApplied = (formattedContent: Array<InstitutionStat>, filters: Array<string>) => {
    return formattedContent.filter(row => containsFilters(row, filters));
};


const containsFilters = (row: InstitutionStat, filters: Array<string>) => {
    return Object.values(row).some((value) => containsSomeFilters(value, filters));
}

const containsSomeFilters = (value: any, filters: Array<string>): boolean => {
    if (typeof value === 'object') {
        return Object.values(value).some((v) => containsSomeFilters(v, filters));
    }
    return filters.some((filter: string) => (value && value.toString().toLowerCase().includes(filter.toLowerCase( ))));
}

const onChartClick = (event: any, chartContext: any, config: any) => {
    const institution = store.institutionStats.value.at(config.dataPointIndex);
    goToAuthorsPage('institution', institution!.institution.name);
}

</script>