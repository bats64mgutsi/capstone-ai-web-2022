<template>
  <div>
    <main>
      <!-- navbar -->
      <Navbar />

      <router-view />
    </main>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from "vue-router";
import Navbar from "./ui-components/nav.vue";
import { watch } from "vue";
import store from "./store";
import { useToast } from "./app-utils";
import { AppMessage } from "./core";

const router = useRouter();
const toast = useToast();

watch(store.message, (value: AppMessage) => {
  if (value && value.type) {
    switch(value.type) {
      case 'error':
        toast.error(value.message);
        break;
      case 'success':
        toast.success(value.message);
        break;
      case 'warning':
        toast.warn(value.message);
        break;
      default:
        toast.warn(value.message);
    }
  }
});
</script>
