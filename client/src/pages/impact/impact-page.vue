<template>
    <div class="w-11/12 mt-2 mx-auto overflow-x-auto relative shadow-md sm:rounded-lg">
        This is the impact page
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, watch } from "vue";
import store from "../../store";
import { refreshData } from "../util";
import { useRouter } from "vue-router";
</script>