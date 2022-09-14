import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainPage from "../pages/main/main-page.vue";
import AuthorPage from "../pages/author/author-page.vue";
import InstitutionPage from "../pages/institution/institution-page.vue";
import SubfieldPage from "../pages/subfield/subfield-page.vue";
import CommunityPage from "../pages/community/community-page.vue";
import ImpactPage from "../pages/impact/impact-page.vue";
import ProfilePage from "../pages/profile/profile-page.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "main",
        component: MainPage
    },
    {
        path: "/authors/search/field/:field/value/:value",
        name: "authors",
        component: AuthorPage
    },
    {
        path: "/authors/:id",
        name: "profile",
        component: ProfilePage
    },
    {
        path: "/authors",
        name: "authors",
        component: AuthorPage
    },
    {
        path: "/institutions",
        name: "institutions",
        component: InstitutionPage
    },
    {
        path: "/subfields",
        name: "subfields",
        component: SubfieldPage
    },
    {
        path: "/community",
        name: "community",
        component: CommunityPage
    },
    {
        path: "/impact",
        name: "impact",
        component: ImpactPage
    },

];

export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});