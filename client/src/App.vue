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

const router = useRouter();
</script>
