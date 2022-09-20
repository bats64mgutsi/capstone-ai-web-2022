<template>  
    <div class="w-11/12 mt-2 mx-auto overflow-x-auto relative shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th scope="col" class="py-3 px-6">
                        Rating
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Number of researchers
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Number of publications
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="stat in stats" class="cursor-pointer bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="py-4 px-6">
                        {{stat.rating}}
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
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, watch } from "vue";
import store from "../../store";
import { refreshData } from "../util";
import { useRouter, useRoute } from "vue-router";

const stats = store.communityStats;
const router = useRouter();
const route = useRoute();

onMounted(async () => {
    try {
        refreshData();
    } catch (e) {
        console.log("Error: ", e);
    }
});

const goToAuthorsPage = (field: string, value: string) => {
    router.push({ name: "authors", params: { field, value } });
}
</script>