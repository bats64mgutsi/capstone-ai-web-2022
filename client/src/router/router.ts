import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainPage from "../pages/main/main-page.vue";
import ProfilePage from "../pages/profile/profile-page.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "main",
        component: MainPage
    },
    {
        path: "/authors/:id",
        name: "profile",
        component: ProfilePage
    },

];

export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});