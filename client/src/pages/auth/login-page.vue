<template>
    <div class="w-11/12 mt-2 mx-auto relative">
        <div class="w-4/12 mx-auto py-6 px-6 lg:px-8">
            <h3 class="mb-4 text-xl text-center font-medium text-gray-900 dark:text-white">Login to account</h3>
            <form @submit.prevent="login" class="mt-8 space-y-6" action="#">
                <div>
                    <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Your email</label>
                    <input type="email" v-model="loginCreds.email" name="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="email" required>
                </div>
                <div>
                    <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Your password</label>
                    <input type="password" v-model="loginCreds.password" name="password" id="password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required>
                </div>
                <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Login</button>
            </form>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { reactive, onMounted } from "vue";
import { backendClient } from "../../api";
import { isLoggedIn, handleError, logAdminIn, goToPage } from "../util";
import { useRouter } from "vue-router";

const router = useRouter();

onMounted(() => {
  if (isLoggedIn()) {
    // TODO: Redirect admin to authors page
  }
});

const loginCreds = reactive({ email: "", password: "" });

const login = async () => {
    try {
        // Must uncomment
        await logAdminIn({
            email: loginCreds.email,
            password: loginCreds.password,
        });

        // goToPage('authors');
        router.push({ path: '/authors' });
    } catch (e) {
        handleError(e);
    }
};
</script>  