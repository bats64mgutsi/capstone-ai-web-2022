import store from "../store";
import { backendClient } from "../api";
import { 
    UploadNrfResearchersArgs,
    getAccessToken, 
    Author, 
    AuthorProfile, 
    InstitutionStat,
    SubfieldStat,
    CommunityStat,
    deleteAccessToken, 
    AdminLoginArgs,
    setAccessToken
} from "../core";
import { Buffer } from 'buffer';

export const refreshData = async () => {
    // if (!store.authors.value.length) {
    //     await getAndSetAuthors();
    // }

    // if (store.currentlySelectedAuthorId.value) {
    //     await getAndSetAuthorProfile(store.currentlySelectedAuthorId.value);
    // }

    // if (!store.institutionStats.value.length) {
    //     await getAndSetInstitutionStats();
    // }

    // if (!store.subfieldStats.value.length) {
    //     await getAndSetSubfieldStats();
    //     const stats = await getSubfieldStatsFromBackend();
    //     setSubfieldStatsInState(stats);
    // }

    // if (!store.communityStats.value.length) {
    //     const stats = await getCommunityStatsFromBackend();
    //     setCommunityStatsInState(stats);
    // }

    if (!store.aiFilters.value.length) {
        // const stats = await getAIFiltersFromBackend();
        const stats = [
            'AI Filter 1',
            'AI Filter 2',
            'AI Filter 3',
            'AI Filter 4',
            'AI Filter 5',
            'AI Filter 6',
        ]
        setAIFiltersInState(stats);
    }
}

const getAndSetAuthors = async () => {
    const authors = await getAuthorsFromBackend();
    setAuthorsInState(authors);
}

const getAuthorsFromBackend = async () => {
    return await backendClient().getAuthors();
}

const setAuthorsInState = (authors: Author[]) => {
    store.setAuthors(authors);
}

const getAndSetAuthorProfile = async (authorId: string) => {
    const profile = await getAuthorProfileFromBackend(authorId);
    setCurrentlySelectedAuthorProfileInState(profile);
}

const getAuthorProfileFromBackend = async (authorId: string) => {
    return await backendClient().getAuthorProfile(authorId);
}

const setCurrentlySelectedAuthorProfileInState = async (profile: AuthorProfile) => {
    store.setCurrentlySelectedAuthorProfile(profile);
}

const getAndSetInstitutionStats = async () => {
    const stats = await getInstitutionStatsFromBackend();
    setInstitutionStatsInState(stats);
}

const getInstitutionStatsFromBackend = async () => {
    return await backendClient().getInstitutionStats();
}

const setInstitutionStatsInState = (stats: InstitutionStat[]) => {
    store.setInstitutionStats(stats);
}

const getAndSetSubfieldStats = async () => {
    const stats = await getSubfieldStatsFromBackend();
    setSubfieldStatsInState(stats);
}

const getSubfieldStatsFromBackend = async () => {
    return await backendClient().getSubfieldStats();
}

const setSubfieldStatsInState = (stats: SubfieldStat[]) => {
    store.setSubfieldStats(stats);
}

const getAndSetCommunityStats = async () => {
    const stats = await getCommunityStatsFromBackend();
    setCommunityStatsInState(stats);
}

const getCommunityStatsFromBackend = async () => {
    return await backendClient().getCommunityStats();
}

const setCommunityStatsInState = (stats: CommunityStat[]) => {
    store.setCommunityStats(stats);
}

const getAIFiltersFromBackend = async () => {
    return await backendClient().getAIFilters();
}

const setAIFiltersInState = (filters: string[]) => {
    store.setAIFilters(filters);
}

export const uploadNrfResearchers = async (data: UploadNrfResearchersArgs) => {
    await backendClient().uploadNrfResearchers(data);
}

export const isLoggedIn = () => {
    return getAccessToken() !== null;
}

export const handleError = (e: any) => {
    console.log('e: ', e.message);
    store.setMessage({ type: "error", message: e.message });
}

export const logAdminIn = async (args: AdminLoginArgs) => {
    await backendClient().login(args);
    const token = Buffer.from(`${args.username}:${args.password}`).toString('base64');
    setAccessToken(token);
    store.setAdmin({ username: args.username });
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

const getAndSetAIFilters = async () => {
    const aiFilters = await getAIFiltersFromBackend();
    setAIFiltersInState(aiFilters);
}

export const modifyFilters = async (modifiedFilters: string[]) => {
    // await backendClient().modifyFilters(modifiedFilters);
    store.setAIFilters(modifiedFilters);
}

const lowerAndRemoveWhitespace = (text: string) => {
    return text.trim().replace(/ +/g, ' ').toLowerCase();
}

export const isFilter = (filter: string) => {
    return store.aiFilters.value.some(f => lowerAndRemoveWhitespace(f) === lowerAndRemoveWhitespace(filter));
}