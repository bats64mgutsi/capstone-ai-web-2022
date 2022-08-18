<template>
    <section class="w-11/12 mx-auto">
        <div class="flex">
            <div class="w-1/5 flex justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-30 w-30 border-grey" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
            </div>
            <div v-if="profile" class="grow flex flex-col justify-center">
                <span class="bg-gray-100 text-gray-800 text-sm font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300 mb-2">Surname: {{profile.author.surname}}</span>
                <span class="bg-gray-100 text-gray-800 text-sm font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300 mb-2">Initials: {{profile.author.initials}}</span>
                <span class="bg-gray-100 text-gray-800 text-sm font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300 mb-2">Title: {{profile.author.title}}</span>
                <span class="bg-gray-100 text-gray-800 text-sm font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300 mb-2">Institution: {{profile.author.institution}}</span>
                <span class="bg-gray-100 text-gray-800 text-sm font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300 mb-2">Subfields: {{getArrayStringRepresentation(profile.subFields)}}</span>
            </div>
        </div>
        <div class="mt-3">
            <div class="pb-4 bg-white dark:bg-gray-900">
                <label for="table-search" class="sr-only">Search</label>
                <div class="relative mt-1">
                    <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input type="text" id="table-search" class="block p-2 pl-10 w-80 text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search publications">
                </div>
            </div>
            <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" class="py-3 px-6">
                            Title
                        </th>
                        <th scope="col" class="py-3 px-6">
                            Year
                        </th>
                        <th scope="col" class="py-3 px-6">
                            Citations
                        </th>
                    </tr>
                </thead>
                <tbody v-if="profile">
                    <tr v-for="publication in profile.publications" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                        <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                            <a :href="publication.externalLink" target=”_blank”>{{publication.title}}</a>
                        </th>
                        <td class="py-4 px-6">
                            {{publication.numberOfCitations}}
                        </td>
                        <td class="py-4 px-6">
                            {{publication.year}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </section>
</template>

<script lang="ts" setup>
import store from "../../store";
import { refreshData } from "../util";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const profile = store.currentlySelectedAuthorProfile;

onMounted(async () => {
  try {
    store.setCurrentlySelectedAuthor(route.params.id as string);
    refreshData();
  } catch (e) {
    console.log("Error: ", e);
  }
});

const getArrayStringRepresentation = (array: string[]) => {
    return array.toString();
}

</script>