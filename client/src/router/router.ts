import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainPage from "../pages/main/main-page.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "main",
        component: MainPage
    },
];

export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});