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
    setAccessToken,
    Institution,
    OverallStat,
    ChartData
} from "../core";
import { Buffer } from 'buffer';

export const refreshData = async () => {
    if (!store.authors.value.length) {
        await getAndSetAuthors();
    }

    if (store.currentlySelectedAuthorId.value) {
        await getAndSetAuthorProfile(store.currentlySelectedAuthorId.value);
    }

    if (!store.institutionStats.value.length) {
        await getAndSetInstitutionStats();
    }

    if (!store.subfieldStats.value.length) {
        await getAndSetSubfieldStats();
    }

    if (!store.overallStats.value) {
        await getAndSetOverallStats();
    }

    if (!store.aiFilters.value.length) {
        const stats = await getAIFiltersFromBackend();
        // const stats = [
        //     'AI Filter 1',
        //     'AI Filter 2',
        //     'AI Filter 3',
        //     'AI Filter 4',
        //     'AI Filter 5',
        //     'AI Filter 6',
        // ]
        setAIFiltersInState(stats);
    }
}

const getAndSetAuthors = async () => {
    const authors = await getAuthorsFromBackend();
    // const authors = [
    //     { id: '1', surname: 'surname 1', initials: 'initials 1', title: 'Prof', institution: 'institution 1', rating: 'A' },
    //     { id: '2', surname: 'surname 2', initials: 'initials 2', title: 'Prof', institution: 'institution 1', rating: 'Y' },
    //     { id: '3', surname: 'surname 3', initials: 'initials 3', title: 'Prof', institution: 'institution 1', rating: 'P' },
    //     { id: '4', surname: 'surname 4', initials: 'initials 4', title: 'Prof', institution: 'institution 1', rating: 'B' },
    //     { id: '5', surname: 'surname 5', initials: 'initials 5', title: 'Prof', institution: 'institution 2', rating: 'C' },
    //     { id: '6', surname: 'surname 6', initials: 'initials 6', title: 'Prof', institution: 'institution 2', rating: 'A' },
    //     { id: '7', surname: 'surname 7', initials: 'initials 7', title: 'Prof', institution: 'institution 2', rating: 'P' },
    //     { id: '8', surname: 'surname 8', initials: 'initials 8', title: 'Prof', institution: 'institution 2', rating: 'P' },
    //     { id: '9', surname: 'surname 8', initials: 'initials 9', title: 'Prof', institution: 'institution 3', rating: 'B' },
    //     { id: '10', surname: 'surname 10', initials: 'initials 10', title: 'Prof', institution: 'institution 3', rating: 'C' },
    //     { id: '11', surname: 'surname 11', initials: 'initials 11', title: 'Prof', institution: 'institution 3', rating: 'Y' },
    //     { id: '12', surname: 'surname 12', initials: 'initials 12', title: 'Prof', institution: 'institution 3', rating: 'B' },
    //     { id: '13', surname: 'surname 13', initials: 'initials 13', title: 'Prof', institution: 'institution 4', rating: 'P' },
    //     { id: '14', surname: 'surname 14', initials: 'initials 14', title: 'Prof', institution: 'institution 4', rating: 'A' },
    //     { id: '15', surname: 'surname 15', initials: 'initials 15', title: 'Prof', institution: 'institution 4', rating: 'C' },
    //     { id: '16', surname: 'surname 16', initials: 'initials 16', title: 'Prof', institution: 'institution 4', rating: 'C' },
    //     { id: '17', surname: 'surname 17', initials: 'initials 17', title: 'Prof', institution: 'institution 5', rating: 'B' },
    //     { id: '18', surname: 'surname 18', initials: 'initials 18', title: 'Prof', institution: 'institution 5', rating: 'C' },
    //     { id: '19', surname: 'surname 19', initials: 'initials 19', title: 'Prof', institution: 'institution 5', rating: 'A' },
    //     { id: '20', surname: 'surname 20', initials: 'initials 20', title: 'Prof', institution: 'institution 5', rating: 'Y' },
    // ];
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
    // const profile = {
    //     author: {id: '1', surname: 'Surname 1', initials: 'initals 1', title: 'Prof', institution: 'institution 1', rating: 'B' },
    //     subfields: ['subfield 1', 'subfield 2', 'subfield 3', 'subfield 4', 'subfield 5'],
    //     publications: [
    //         { id: 'id 1', title: 'title 1', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 2', title: 'title 2', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 3', title: 'title 3', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 4', title: 'title 4', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 5', title: 'title 5', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 6', title: 'title 6', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 7', title: 'title 7', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 8', title: 'title 8', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 9', title: 'title 9', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 10', title: 'title 10', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 11', title: 'title 11', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //         { id: 'id 12', title: 'title 12', citationCount: 34, year: '2013', externalLink: 'https://reader.elsevier.com/reader/sd/pii/S2214509515300176?token=6AF344EE0FC0F07E342A867B9E30A21FA965A4579625F3F9FC44B8237F65512942868FC74238B6FE1B1AE4392C3F51E2&originRegion=eu-west-1&originCreation=20220925170626' },
    //     ],
    //     citationCount: 234
    // }
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
    // const stats = [
    //     { institution: { id: '1', name: 'institution 1', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '2', name: 'institution 2', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '3', name: 'institution 3', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '4', name: 'institution 4', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '5', name: 'institution 5', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '6', name: 'institution 6', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '7', name: 'institution 7', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '8', name: 'institution 8', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '9', name: 'institution 9', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '10', name: 'institution 10', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '11', name: 'institution 11', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    //     { institution: { id: '12', name: 'institution 12', latitude: 23, longitude: 56 }, noAuthors: 23, noPublications: 2300 },
    // ];
    setInstitutionStatsInState(stats);
}

const getInstitutionStatsFromBackend = async () => {
    return await backendClient().getInstitutionStats();
}

const setInstitutionStatsInState = (stats: InstitutionStat[]) => {
    store.setInstitutionStats(stats);
}

const getAndSetOverallStats = async () => {
    const stats = await backendClient().getOverallStats();
    // const stats = {
    //     noAuthorsCurrentYear: 123,
    //     noAuthorsPreviousYear: 256,
    //     noPublicationsCurrentYear: 3729,
    //     noPublicationsPreviousYear: 2345,
    //     noCitationsCurrentYear: 10372,
    //     noCitationsPreviousYear: 20452,
    //     ratedACurrentYear: 34,
    //     ratedBCurrentYear: 65,
    //     ratedCCurrentYear: 23,
    //     ratedPCurrentYear: 10,
    //     ratedYCurrentYear: 7,
    //     ratedAPreviousYear: 21,
    //     ratedBPreviousYear: 45,
    //     ratedCPreviousYear: 29,
    //     ratedPPreviousYear: 40,
    //     ratedYPreviousYear: 12,
    //     subfields: [
    //         { subfield: { id: '1', name: 'subfield 1' }, numberOfAuthorsCurrentYear: 23, numberOfAuthorsPrevYear: 90 },
    //         { subfield: { id: '2', name: 'subfield 2' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 80 },
    //         { subfield: { id: '3', name: 'subfield 3' }, numberOfAuthorsCurrentYear: 45, numberOfAuthorsPrevYear: 74 },
    //         { subfield: { id: '4', name: 'subfield 4' }, numberOfAuthorsCurrentYear: 56, numberOfAuthorsPrevYear: 23 },
    //         { subfield: { id: '5', name: 'subfield 5' }, numberOfAuthorsCurrentYear: 65, numberOfAuthorsPrevYear: 35 },
    //         { subfield: { id: '6', name: 'subfield 6' }, numberOfAuthorsCurrentYear: 7, numberOfAuthorsPrevYear: 28 },
    //         { subfield: { id: '7', name: 'subfield 7' }, numberOfAuthorsCurrentYear: 10, numberOfAuthorsPrevYear: 74 },
    //         { subfield: { id: '8', name: 'subfield 8' }, numberOfAuthorsCurrentYear: 20, numberOfAuthorsPrevYear: 39 },
    //         { subfield: { id: '9', name: 'subfield 9' }, numberOfAuthorsCurrentYear: 25, numberOfAuthorsPrevYear: 10 },
    //         { subfield: { id: '10', name: 'subfield 10' }, numberOfAuthorsCurrentYear: 63, numberOfAuthorsPrevYear: 28 },
    //         { subfield: { id: '11', name: 'subfield 11' }, numberOfAuthorsCurrentYear: 32, numberOfAuthorsPrevYear: 31 },
    //         { subfield: { id: '12', name: 'subfield 12' }, numberOfAuthorsCurrentYear: 28, numberOfAuthorsPrevYear: 52 },
    //         { subfield: { id: '13', name: 'subfield 13' }, numberOfAuthorsCurrentYear: 54, numberOfAuthorsPrevYear: 64 },
    //         { subfield: { id: '14', name: 'subfield 14' }, numberOfAuthorsCurrentYear: 42, numberOfAuthorsPrevYear: 72 },
    //         { subfield: { id: '15', name: 'subfield 15' }, numberOfAuthorsCurrentYear: 19, numberOfAuthorsPrevYear: 4 },
    //     ]
    // }
    setOverallStatsInState(stats);
}

const setOverallStatsInState = (stats: OverallStat) => {
    store.setOverallStats(stats);
}

const getAndSetSubfieldStats = async () => {
    const stats = await getSubfieldStatsFromBackend();
    // const stats = [
    //     { subfield: {id: '1', name: 'subfield 1' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '2', name: 'subfield 2' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '3', name: 'subfield 3' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '4', name: 'subfield 4' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '5', name: 'subfield 5' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '6', name: 'subfield 6' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '7', name: 'subfield 7' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '8', name: 'subfield 8' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '9', name: 'subfield 9' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '10', name: 'subfield 10' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '11', name: 'subfield 11' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '12', name: 'subfield 12' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    //     { subfield: {id: '13', name: 'subfield 13' }, numberOfAuthorsCurrentYear: 34, numberOfAuthorsPrevYear: 64 },
    // ];
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
    console.log(e.stack);
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
    await backendClient().modifyFilters(modifiedFilters);
    store.setAIFilters(modifiedFilters);
    store.setMessage({type: 'success', message: 'Successfully updated filters'});
}

const lowerAndRemoveWhitespace = (text: string) => {
    return text.trim().replace(/ +/g, ' ').toLowerCase();
}

export const isFilter = (filter: string) => {
    return store.aiFilters.value.some(f => lowerAndRemoveWhitespace(f) === lowerAndRemoveWhitespace(filter));
}

export const getOverallStats = async () => {
    if (!store.overallStats) {
        await getAndSetOverallStats();
    }
    return store.overallStats.value;
}

export const getChartData = () => {
    if (store.overallStats.value) {
        return {
            pieData: {
                rating_currentYear: {
                    series: [
                        store.overallStats.value.ratedACurrentYear,
                        store.overallStats.value.ratedBCurrentYear,
                        store.overallStats.value.ratedCCurrentYear,
                        store.overallStats.value.ratedPCurrentYear,
                        store.overallStats.value.ratedYCurrentYear,
                    ],
                    chartOptions: {
                        chart: {
                            width: 380,
                            type: 'pie',
                        },
                        labels: ['A', 'B', 'C', 'Y', 'P'],
                        responsive: [{
                            breakpoint: 480,
                            options: {
                                chart: {
                                    width: 200
                                },
                                legend: {
                                    position: 'bottom'
                                }
                            }
                        }]
                    },
                },
                rating_prevYear: {
                    series: [
                        store.overallStats.value.ratedAPreviousYear,
                        store.overallStats.value.ratedBPreviousYear,
                        store.overallStats.value.ratedCPreviousYear,
                        store.overallStats.value.ratedPPreviousYear,
                        store.overallStats.value.ratedYPreviousYear,
                    ],
                    chartOptions: {
                        chart: {
                            width: 380,
                            type: 'pie',
                        },
                        labels: ['A', 'B', 'C', 'P', 'Y'],
                        responsive: [{
                            breakpoint: 480,
                            options: {
                                chart: {
                                    width: 200
                                },
                                legend: {
                                    position: 'bottom'
                                }
                            }
                        }]
                    },
                }
            }
        };
    }
}