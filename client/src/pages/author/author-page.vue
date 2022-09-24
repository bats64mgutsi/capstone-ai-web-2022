<template>  
    <div class="w-11/12 mt-2 mx-auto overflow-x-auto relative shadow-md sm:rounded-lg">
        <div class="flex flex-row justify-between items-center mb-3">
            <div class="bg-white dark:bg-gray-900 flex flex-row items-center">
                <label for="table-search" class="sr-only">Search</label>
                <div class="relative mt-1">
                    <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input type="text" id="table-search" class="block p-2 pl-10 w-80 text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search for authors">
                </div>
            </div>

            <button v-if="isLoggedIn()" class="flex flex-row items-center bg-blue-600 hover:bg-blue-400 text-white rounded text-sm p-2 h-10" type="button" data-modal-toggle="large-modal">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd" />
                </svg>
                Upload authors
            </button>

            <div id="large-modal" tabindex="-1" data-modal-placement="top-center" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full">
                <div class="relative p-4 w-full max-w-6xl h-full md:h-auto">
                    <!-- Modal content -->
                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                        <!-- Modal header -->
                        <div class="flex justify-between items-center p-5 rounded-t border-b dark:border-gray-600">
                            <h3 class="text-xl font-medium text-gray-900 dark:text-white">
                                Upload CSV file
                            </h3>
                            <button @click="handleCloseEvent" type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-toggle="large-modal">
                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                <span class="sr-only">Close modal</span>
                            </button>
                        </div>
                        <!-- Modal body -->
                        <div class="py-6 px-6 lg:px-8">
                            <div class="flex justify-center items-center w-full" v-if="!uploadedFile">
                                <label for="dropzone-file" class="flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                                    <div class="flex flex-col justify-center items-center pt-5 pb-6">
                                        <svg aria-hidden="true" class="mb-3 w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                        <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to upload</span> or drag and drop</p>
                                        <p class="text-xs text-gray-500 dark:text-gray-400">Only CSV and Excel files</p>
                                    </div>
                                    <input id="dropzone-file" type="file" class="hidden" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" @change="handleFileUpload($event)" />
                                </label>
                            </div>
                            <div class="shadow-md sm:rounded-lg p-3" v-else>
                                <div class="flex justify-between items-center pb-4 bg-white dark:bg-gray-900">
                                    <label for="table-search" class="sr-only">Search</label>
                                    <div class="relative">
                                        <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                            <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                                        </div>
                                        <input type="text" v-model="searchFileInput" @input="handleSearchFileInput()" id="table-search-users" class="block p-2 pl-10 w-80 text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search file">
                                    </div>
                                </div>
                                <div class="overflow-x-auto relative">
                                    <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                                        <thead v-if="headers.length" class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                                            <tr>
                                                <th v-for="header in headers" scope="col" class="py-3 px-6">
                                                    {{truncate(header, 10)}}
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody v-if="!isProcessing">
                                            <tr v-for="row in pageContent" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                                                <td v-for="data in row" class="py-4 px-6">
                                                    {{truncate(data.columnValue.toString(), 10)}}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                
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
                        </div>
                        <!-- Modal footer -->
                        <div v-if="!isProcessing" class="flex items-center p-6 space-x-2 rounded-b border-t border-gray-200 dark:border-gray-600">
                            <button @click="handleSubmitEvent()" type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
                            <button @click="handleCloseEvent" data-modal-toggle="large-modal" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th scope="col" class="py-3 px-6">
                        Surname
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Initials
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Title
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Rating
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Institution
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="author in authors" @click="goToProfile(`${author.surname}`)" class="cursor-pointer bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="py-4 px-6">
                        {{author.surname}}
                    </td>
                    <td class="py-4 px-6">
                        {{author.initials}}
                    </td>
                    <td class="py-4 px-6">
                        {{author.title}}
                    </td>
                    <td class="py-4 px-6">
                        {{author.rating}}
                    </td>
                    <td class="py-4 px-6">
                        {{author.institution}}
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from "vue";
import store from "../../store";
import { refreshData, uploadNrfResearchers, isLoggedIn } from "../util";
import { useRouter, useRoute } from "vue-router";
import readXlsxFile from 'read-excel-file';
import truncate from 'truncate';
import { NrfResearcher } from '../../core';

const authors = store.authors;
const router = useRouter();
const route = useRoute();

const uploadedFile = ref<File>();
const content = ref<Array<Array<any>>>([]);
const formattedContent = ref<Array<Array<{ columnName: string; columnValue: any}>>>([]);

const searchedContent = ref<Array<Array<{ columnName: string; columnValue: any}>>>([]);
const pageContent = ref<Array<Array<{ columnName: string; columnValue: any}>>>([]);
const page = ref<number>(1);
const day = ref<number>(0);
const month = ref<number>(0);
const year = ref<number>(0);
const headers = ref<Array<string>>([]);
const isProcessing = ref<Boolean>(false);
const searchFileInput = ref<string>('');

const searchField = ref<Array<string> | string>('');
const searchValue = ref<Array<string> | string>('');


onMounted(async () => {
  try {
    refreshData();

    if (route.params.field && route.params.value) {
        console.log('router.params: ', route.params.field, route.params.value);
        searchField.value = route.params.field;
        searchValue.value = route.params.value;
    }
  } catch (e) {
    console.log("Error: ", e);
  }
});

const goToProfile = (authorId: string) => {
    router.push({ name: "profile", params: { id: authorId } });
}

const handleFileUpload = async ( event: any ) => {
    uploadedFile.value = event.target.files[0];
    parseUploadedFile();
}

const parseUploadedFile = () => {
    if (uploadedFile.value) {
        readXlsxFile(uploadedFile.value).then((rows) => {
            content.value = rows;
            isProcessing.value = true;
            processContent();
            isProcessing.value = false;
        });
    }
}

const processContent = () => {
    console.log('Inside processContent...');
    setDayMonthAndYear();
    setHeaders();
    formatContent();
    setSearchedContent();
    setPageContent();
}

const setDayMonthAndYear = () => {
    if (content.value[0][0].includes('Updated')) {
        const data = content.value[0][0].split(' ');
        data.splice(0, 1);
        day.value = parseInt(data[0]);
        month.value = getMonthNumber(data[1]);
        year.value = parseInt(data[2]);
        console.log('Inside setYearAndMonth, day: ', day.value);
        console.log('Inside setYearAndMonth, month: ', month.value);
        console.log('Inside setYearAndMonth, year: ', year.value);
    }
}

const getMonthNumber = (month: string) => {
    month = month.toLowerCase().trim().substring(0, 3);
    switch (month) {
        case 'jan':
            return 1;
        case 'feb':
            return 1;
        case 'mar':
            return 3;
        case 'apr':
            return 4;
        case 'may':
            return 5;
        case 'jun':
            return 6;
        case 'jul':
            return 7;
        case 'aug':
            return 8;
        case 'sep':
            return 9;
        case 'oct':
            return 11;
        case 'nov':
            return 1;
        case 'dec':
            return 12;
        default:
            return 0;
    }
}

const setHeaders = () => {
    if (content.value.length) {
        headers.value = content.value[0].includes('Surname') ? content.value[0] : content.value[1];
        removeUnnecessaryHeaders();
    }
}

const removeUnnecessaryHeaders = () => {
    headers.value = headers.value.filter((header) => !(['Rating Start', 'Rating End'].includes(header)));
    console.log('headers.value: ', headers.value);
}

const formatContent = () => {
    const contentWithoutHeaders = getContentWithoutHeaders();
    removeUnnecessaryContent(contentWithoutHeaders)
    const contentWithoutUnnecessaryContent = JSON.parse(JSON.stringify(contentWithoutHeaders));
    contentWithoutUnnecessaryContent.forEach((row: Array<any>) => {
        headers.value.forEach((header, index) => {
            row[index] = { columnName: header, columnValue: row[index] };
        });
    });

    formattedContent.value = JSON.parse(JSON.stringify(contentWithoutUnnecessaryContent));
}

const getContentWithoutHeaders = () => {
    const content_copy = JSON.parse(JSON.stringify(content.value));
    return content_copy[0].includes('Surname') ? content_copy.slice(1) : content_copy.slice(2);
}

const removeUnnecessaryContent = (content: Array<Array<any>>) => {
    content.forEach((value: Array<any>) => value.splice(5, 2));
}

const getContentWithFiltersApplied = (formattedContent: Array<Array<{columnName: string; columnValue: any }>>, filters: Array<string>) => {
    return formattedContent.filter(row => containsFilters(row, filters));
};

const setPageContent = () => {
    pageContent.value = searchedContent.value.slice(((page.value - 1) * 10), getValidEndIndex(searchedContent.value, ((page.value - 1) * 10) + 10));
}

const getValidEndIndex = (content: Array<Array<{ columnName: string; columnValue: any}>>, endIndex: number) => {
    return (endIndex >= content.length) ? (content.length + 1) : endIndex;
}

const handlePreviousBtnClickEvent = () => {
    page.value = ((page.value - 1) <= 0) ? page.value : (isValidPageNumber(page.value - 1) ? (page.value - 1) : page.value);
    setPageContent();
}

const handleNextBtnClickEvent = () => {
    console.log('Inside handleNextBtnClickEvent');
    console.log('searchedContent.value: ', searchedContent.value);
    console.log('searchedContent.value.length: ', searchedContent.value.length);
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


const containsFilters = (row: Array<{columnName: string; columnValue: string}>, filters: Array<string>) => {
    return row.some((value) => containsSomeFilters(value, filters));
}

const containsSomeFilters = (value: {columnName: string; columnValue: string}, filters: Array<string>) => {
    return filters.some((filter: string) => (value.columnValue && value.columnValue.toString().toLowerCase().includes(filter.toLowerCase( ))));
}

const setSearchedContent = () => {
    searchedContent.value = JSON.parse(JSON.stringify(formattedContent.value));
}

const resetPage = () => {
    page.value = 1;
}

const handleSearchFileInput = () => {
    console.log('searchFileInput: ', searchFileInput.value);
    isProcessing.value = true;
    
    setSearchedContent();

    if (searchFileInput.value.length) {
        searchedContent.value = getContentWithFiltersApplied(searchedContent.value, [searchFileInput.value]);
        console.log('searchedContent after filters: ', searchedContent.value);
    }

    resetPage();
    setPageContent();

    isProcessing.value = false;
}

const handleSubmitEvent = async () => {
    try {
        isProcessing.value = true;
        const data = getContentForBackend();
        console.log('data inside handleSubmitEvent: ', data);
        await uploadNrfResearchers({
            year: year.value,
            data
        });
        resetFileUploadingData();
        isProcessing.value = false;
    }
    catch (e) {
        console.log(e);
    }
}

const handleCloseEvent = () => {
    resetFileUploadingData();
}

const resetFileUploadingData = () => {
    uploadedFile.value = undefined;
    content.value = [];
    formattedContent.value = [];
    searchedContent.value = [];
    pageContent.value = [];
    page.value = 1;
    day.value = 0;
    month.value = 0;
    year.value = 0;
    headers.value = [];
    isProcessing.value = false;
    searchFileInput.value = '';;
}

const getContentForBackend = () => {
    const data = JSON.parse(JSON.stringify(formattedContent.value));
    formatResearchFieldValues(data);
    return convertRowsToObjects(data);
}

const convertRowsToObjects = (data: Array<Array<{ columnName: string; columnValue: any}>>) => {
    const convertedData = data.map((row: Array<{ columnName: string; columnValue: any}>) => convertRowToObject(row));
    console.log('convertedData in convertRowsToObjects: ', convertedData);
    return convertedData;
}

const convertRowToObject = (row: Array<{ columnName: string; columnValue: any}>) => {
    let object: NrfResearcher = {
        surname: "",
        initials: "",
        title: "",
        rating: "",
        primaryResearchFields: [],
        secondaryResearchFields: [],
        specialisations: []
    };

    row.forEach((value, index) => {
        object = { ...object, [formatColumnName(headers.value[index])]: value.columnValue };
    });

    return object;
}

const formatColumnName = (columnName: string) => {
    const columnNameSplit = columnName.split(' ');
    columnNameSplit[0] = columnNameSplit[0].toLowerCase();
    return columnNameSplit.join('');
}

const formatResearchFieldValues = (data: Array<Array<{ columnName: string; columnValue: any}>>) => {
    data.forEach((row) => {
        row.forEach((value) => {
            if (value.columnValue && ['Primary Research Fields', 'Secondary Research Fields', 'Specialisations'].includes(value.columnName)) {
                value.columnValue = value.columnValue.split('; ');
            }
        })
    });

    console.log('inside formatResearchFieldValues data: ', data);
}
</script>
