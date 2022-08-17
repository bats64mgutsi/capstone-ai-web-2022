<template>
    <section class="w-11/12 mx-auto">
        <div v-if="profile" class="flex flex-col"  style="border: 1px solid #000">
            <span>Surname: ${{profile.author.surname}}</span>
            <span>Initials: ${{profile.author.initials}}</span>
            <span>Title: ${{profile.author.title}}</span>
            <span>Institution: ${{profile.author.institution}}</span>
            <span>Subfields: ${{profile.subFields}}</span>
        </div>
        <div class="mt-3">
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
                            <a href="${publication.externalLink}" target=”_blank”>{{publication.title}}</a>
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

const profile = store.currentlySelectedAuthorProfile;

onMounted(async () => {
  try {
    refreshData();
  } catch (e) {
    console.log("Error: ", e);
  }
});

</script>