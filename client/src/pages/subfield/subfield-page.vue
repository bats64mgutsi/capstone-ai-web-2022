<template>  
    <div class="w-11/12 mt-2 mx-auto overflow-x-auto relative shadow-md sm:rounded-lg">
        <div class="flex flex-row justify-between items-center mb-3">
            <div class="bg-white dark:bg-gray-900 flex flex-row items-center">
                <label for="table-search" class="sr-only">Search</label>
                <div class="relative mt-1">
                    <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input type="text" id="table-search" @input="handleSearchInput()" class="block p-2 pl-10 w-80 text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search for subfields">
                </div>
            </div>
        </div>
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th scope="col" class="py-3 px-6">
                        Subfield
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Number of researchers
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Number of publications
                    </th>
                </tr>
            </thead>
            <tbody  v-if="!isProcessing">
                <tr v-for="stat in stats" @click="goToAuthorsPage('specializations', `${stat.subfield}`)" class="cursor-pointer bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="py-4 px-6">
                        {{stat.subfield}}
                    </td>
                    <td class="py-4 px-6">
                        {{stat.numberOfResearchers}}
                    </td>
                    <td class="py-4 px-6">
                        {{stat.numberOfPublications}}
                    </td>
                </tr>
            </tbody>
        </table>
        <nav class="flex justify-between items-center pt-4" aria-label="Table navigation">
            <span v-if="!isProcessing" class="text-sm font-normal text-gray-500 dark:text-gray-400">
                Showing
                <span class="font-semibold text-gray-900 dark:text-white">{{getFirstRecordNumberForGivenPage(page)}}-{{getLastRecordNumberForGivenPage(page)}}</span>
                of <span class="font-semibold text-gray-900 dark:text-white">{{searchedContent.length}}</span>
            </span>
            <ul class="inline-flex items-center -space-x-px">
                <li>
                    <a href="#" @click="handlePreviousBtnClickEvent()" class="inline-flex items-center py-2 px-4 mr-3 text-sm font-medium text-gray-500 bg-white rounded-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                        <svg aria-hidden="true" class="mr-2 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l2.293 2.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg>
                        Previous
                    </a>
                </li>
                <li>
                    <a href="#" @click="handleNextBtnClickEvent()" class="inline-flex items-center py-2 px-4 text-sm font-medium text-gray-500 bg-white rounded-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                        Next
                        <svg aria-hidden="true" class="ml-2 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M12.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-2.293-2.293a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, watch } from "vue";
import store from "../../store";
import { refreshData } from "../util";
import { useRouter, useRoute } from "vue-router";
import { SubfieldStat } from "../../core";

const stats = store.subfieldStats;
const isProcessing = ref<boolean>(false);
const searchInput = ref<string>('');
const searchedContent = ref<Array<SubfieldStat>>([]);
const pageContent = ref<Array<SubfieldStat>>([]);
const page = ref<number>(1);
const router = useRouter();

onMounted(async () => {
    try {
        isProcessing.value = true;
        refreshData();
        initLocalVars();
        isProcessing.value = false;
    } catch (e) {
        console.log("Error: ", e);
    }
});

const initLocalVars = () => {
    setSearchedContent();
    setPageContent();
}

const setSearchedContent = () => {
    searchedContent.value = stats.value.length ? JSON.parse(JSON.stringify(stats.value)) : [];
}

const goToAuthorsPage = (field: string, value: string) => {
    router.push({ name: "authors", params: { field, value } });
}

const handleSearchInput = () => {
    console.log('searchInput: ', searchInput.value);
    isProcessing.value = false;
    
    setSearchedContent();

    if (searchInput.value.length) {
        searchedContent.value = getContentWithFiltersApplied(searchedContent.value, [searchInput.value]);
    }

    setPageContent();

    isProcessing.value = true;
}

const getContentWithFiltersApplied = (content: Array<SubfieldStat>, filters: Array<string>) => {
    return content.filter((value: SubfieldStat)  => containsFilters(value, filters));
};

const containsFilters = (stat: SubfieldStat, filters: Array<string>) => {
    return Object.values(stat).some((value) => containsSomeFilters(value, filters));
}

const containsSomeFilters = (value: any, filters: Array<string>) => {
    return filters.some((filter: string) => (value && value.toString().toLowerCase().includes(filter.toLowerCase( ))));
}

const setPageContent = () => {
    pageContent.value = searchedContent.value.slice(((page.value - 1) * 10), getValidEndIndex(searchedContent.value, ((page.value - 1) * 10) + 10));
}

const getValidEndIndex = (content: Array<SubfieldStat>, endIndex: number) => {
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
</script>