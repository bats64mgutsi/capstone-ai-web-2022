<template>
    <div class="w-11/12 mt-2 mx-auto overflow-x-auto relative">
        <div class="flex flex-row justify-end items-center mb-3">
            <button class="flex flex-row items-center bg-blue-600 hover:bg-blue-400 text-white rounded text-sm p-2 h-10" type="button" data-modal-toggle="add-new-filter-modal">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd" />
                </svg>
                Add new 
            </button>

            <div id="add-new-filter-modal" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
                <div class="relative p-4 w-full max-w-md h-full md:h-auto">
                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                        <button @click="handleCloseEvent" type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white" data-modal-toggle="add-new-filter-modal">
                            <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                        <div class="py-6 px-6 lg:px-8">
                            <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">Add new filter</h3>
                            <form @submit.prevent="addNewFilter" class="space-y-6" action="#">
                                <div>
                                    <input type="filter" v-model="newFilter" name="filter" id="filter" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="Enter filter here" required>
                                </div>
                                <button data-modal-toggle="add-new-filter-modal" type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div id="edit-filter-modal" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
                <div class="relative p-4 w-full max-w-md h-full md:h-auto">
                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                        <button @click="handleCloseEvent" type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white" data-modal-toggle="edit-filter-modal">
                            <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                        <div class="py-6 px-6 lg:px-8">
                            <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">Edit filter</h3>
                            <form @submit.prevent="editFilter" class="space-y-6" action="#">
                                <div>
                                    <input type="filter" v-model="editedFilter" name="filter" id="filter" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="Enter filter here" required>
                                </div>
                                <button data-modal-toggle="edit-filter-modal" type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div id="delete-filter-modal" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
                <div class="relative p-4 w-full max-w-md h-full md:h-auto">
                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                        <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white" data-modal-toggle="delete-filter-modal">
                            <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                        <div class="p-6 text-center">
                            <svg aria-hidden="true" class="mx-auto mb-4 w-14 h-14 text-gray-400 dark:text-gray-200" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                            <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">Are you sure you want to delete the filter: `{{filterToDelete}}`?</h3>
                            <button @click="deleteFilter" data-modal-toggle="delete-filter-modal" type="button" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                                Yes, I'm sure
                            </button>
                            <button data-modal-toggle="delete-filter-modal" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600">No, cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th scope="col" class="py-3 px-6">
                        Filter
                    </th>
                    <!-- <th scope="col" class="py-3 px-6">
                        Action 1
                    </th> -->
                    <th scope="col" class="py-3 px-6">
                        Action
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="filter in computedFilters" class="cursor-pointer bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="cursor-pointer py-4 px-6">
                        {{filter}}
                    </td>
                    <!-- <td @click="handleEditClick(filter)" class="py-4 px-6" data-modal-toggle="edit-filter-modal">
                        <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>
                    </td> -->
                    <td @click="handleDeleteClick(filter)" class="py-4 px-6" data-modal-toggle="delete-filter-modal">
                        <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, watch, computed } from "vue";
import store from "../../store";
import { refreshData, handleError, modifyFilters, isLoggedIn, isFilter } from "../util";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();

onMounted(() => {
  if (!isLoggedIn()) {
    // TODO: Redirect admin to authors page
    router.push({ name: "authors" });
  }

  else {
    refreshData();
  }
});

const filters = store.aiFilters;
const computedFilters = computed(() => {
    console.log('Calling computedFilters...');
    return store.aiFilters.value;
})
const newFilter = ref<string>('');
const originalFilter = ref<string>('');
const editedFilter = ref<string>('');
const filterToDelete = ref<string>('');

const handleCloseEvent = () => {
    resetData();
}

const resetData = () => {
    newFilter.value = '';
}

const handleEditClick = (filter: string) => {
    console.log("Handling Edit click for filter: ", filter);
    originalFilter.value = filter;
    editedFilter.value = filter;
}

const handleDeleteClick = (filter: string) => {
    console.log("Handling delete click for filter: ", filter);
    filterToDelete.value = filter;
    deleteFilter();
}

const addNewFilter = async () => {
    try {
        if (isFilter(newFilter.value)) {
            throw new Error("Filter already exists");
        }

        else {
            const temp = JSON.parse(JSON.stringify(filters.value));
            temp.push(newFilter.value);
            console.log("Modified filters: ", temp);
            modifyFilters(temp);
        }
    } catch (e) {
        handleError(e);
    }
}

const editFilter = () => {
    try {
        if (isFilter(editedFilter.value)) {
            throw new Error("Filter already exists");
        }

        else {
            const temp = JSON.parse(JSON.stringify(filters.value));
            temp.splice(temp.indexOf(originalFilter.value), 1, editedFilter.value);
            console.log("Modified filters: ", temp);
            modifyFilters(temp);
        }
    } catch (e) {
        handleError(e);
    }
}

const deleteFilter = () => {
    try {
        const temp = JSON.parse(JSON.stringify(filters.value));
        temp.splice(filters.value.indexOf(filterToDelete.value), 1);
        console.log("Modified filters: ", temp);
        modifyFilters(temp);
    } catch (e) {
        handleError(e);
    }
}
</script>