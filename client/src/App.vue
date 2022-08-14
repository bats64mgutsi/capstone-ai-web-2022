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
import { onMounted, ref, watch } from "vue";
import store from "./store";
import { useRouter } from "vue-router";
import { AppMessage, getAccessToken, deleteAccessToken } from "./core";
import Navbar from "./ui-components/nav.vue";
import { backendClient } from "./rpc";

const router = useRouter();
</script>
