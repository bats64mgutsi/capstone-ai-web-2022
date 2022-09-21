import store from "../store";
import { backendClient } from "../api";
import { UploadNrfResearchersArgs, getAccessToken, deleteAccessToken, AdminLoginArgs, AIFilterAddArgs } from "../core";

export const refreshData = async () => {
    if (!store.authors.value.length) {
        const authors = await backendClient().getAuthors();
        store.setAuthors(authors);
    }

    if (store.currentlySelectedAuthorId.value) {
        const profile = await backendClient().getAuthorProfile(store.currentlySelectedAuthorId.value);
        store.setCurrentlySelectedAuthorProfile(profile);
    }

    if (!store.institutionStats.value.length) {
        const stats = await backendClient().getInstitutionStats();
        store.setInstitutionStats(stats);
    }

    if (!store.subfieldStats.value.length) {
        const stats = await backendClient().getSubfieldStats();
        store.setSubfieldStats(stats);
    }

    if (!store.communityStats.value.length) {
        const stats = await backendClient().getCommunityStats();
        store.setCommunityStats(stats);
    }

    if (!store.aiFilters.value.length) {
        const filters = await backendClient().getAIFilters();
        store.setAIFilters(filters);
    }
}

export const uploadNrfResearchers = async (data: UploadNrfResearchersArgs) => {
    await backendClient().uploadNrfResearchers(data);
}

export const isLoggedIn = () => {
    return true;
    // return getAccessToken() !== null;
}

export const handleError = (e: any) => {
    store.setMessage({ type: "error", message: e.message });
}

export const logAdminIn = async (args: AdminLoginArgs) => {
    const res = await backendClient().login(args);
    store.setAdmin(res.user);
}

export const goToPage = (page: string) => {
    let path;
    switch(page) {
        case 'login':
            path = isLoggedIn() ? '/admin/authors' : '/admin/auth/login';
            break;
        case 'authors':
            path = isLoggedIn() ? '/admin/authors' : '/authors';
            break;
        case 'institutions':
            path = isLoggedIn() ? '/admin/institutions' : '/institutions';
            break;
        case 'subfields':
            path = isLoggedIn() ? '/admin/subfields' : '/subfields';
            break;
        case 'community':
            path = isLoggedIn() ? '/admin/community/' : '/admin/auth/login';
            break;
        case 'impact':
            path = isLoggedIn() ? '/admin/community/' : '/admin/auth/login';
            break;
        default:
            path = '/authors';
    }
}

export const logAdminOut = () => {
    // TODO
    deleteAccessToken();
}

export const addFilter = async (args: AIFilterAddArgs) => {
    const res = await backendClient().addFilter(args);
    store.setAdmin(res.filter);
}