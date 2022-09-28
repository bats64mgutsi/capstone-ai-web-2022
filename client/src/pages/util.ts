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

/**
 * Makes appropriate calls to the backendclient
 * to the fetch and store author, author profile, institution,
 * overall stat, and ai filter data in the state
 * @param No parameters expected
 * @returns No returns
 */
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
        await getAndSetAIFilters();
    }
}

/**
 * A helper method that gets author data
 * from the backend and sets it into the state
 * @param No parameters expected
 * @returns No returns
 */
const getAndSetAuthors = async () => {
    const authors = await getAuthorsFromBackend();
    setAuthorsInState(authors);
}

/**
 * A helper method that gets author data
 * from the backend
 * @param No parameters expected
 * @returns No returns
 */
const getAuthorsFromBackend = async () => {
    return await backendClient().getAuthors();
}

/**
 * A helper method that sets author data
 * into the state
 * @param authors A lits of authors fetched 
 * from the backend
 * @returns No returns
 */
const setAuthorsInState = (authors: Author[]) => {
    store.setAuthors(authors);
}

/**
 * A helper method that gets an author's profile data
 * from the backend and sets it into the state
 * @param authorId The id of the author
 * @returns No returns
 */
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

/**
 * A helper method that gets an author's profile data
 * from the backend and sets it into the state
 * @param authorId The id of the author
 * @returns No returns
 */
const getAndSetOverallStats = async () => {
    const stats = await backendClient().getOverallStats();
    setOverallStatsInState(stats);
}

const setOverallStatsInState = (stats: OverallStat) => {
    store.setOverallStats(stats);
}

/**
 * A helper method that gets sufield data
 * from the backend and sets it into the state
 * @param No parameters expected
 * @returns No returns
 */
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

/**
 * A helper method that gets ai filter data
 * from the backend and sets it into the state
 * @param No parameters expected
 * @returns No returns
 */
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

export const getInstitutionChartData = () => {
    if(store.institutionStats.value) {
        return {
            chartOptions: {
                plotOptions: {
                    bar: {
                      horizontal: true
                    },
                },

                chart: {
                    type: 'bar',
                    height: 350
                },
                
                dataLabels: {
                    enabled: false
                },

                xaxis: {
                    categories: store.institutionStats.value.map(el => el.institution.name),
                }
            },

            series: [{
                data: store.institutionStats.value.map(el => el.noAuthors)
              }],
          };
    }
}

const getArrayOfHexadecimalColors = (numberOfColors: number) => {
    let array = [];
    for (let index = 0; index < numberOfColors; index++) {
        array.push(randColor());
    }

    return array;
}

const randColor = () =>  {
    return "#" + Math.floor(Math.random()*16777215).toString(16).padStart(6, '0').toUpperCase();
}

const getCustomBarChartDataSeriesForSubfieldCurrYear = () => {
    const dataSeries: number[] = [];
    if (store.overallStats.value) {
        store.overallStats.value.subfields.forEach(subfield => {
            dataSeries.push(subfield.numberOfAuthorsCurrentYear)
        });
    }

    return dataSeries;
}

const getCustomBarChartDataSeriesForSubfieldPrevYear = () => {
    const dataSeries: number[] = [];
    if (store.overallStats.value) {
        store.overallStats.value.subfields.forEach(subfield => {
            dataSeries.push(subfield.numberOfAuthorsPrevYear)
        });
    }

    return dataSeries;
}

const getCustomBarChartXAxisCategoriesForSubfields = () => {
    const dataSeries: string[] = [];
    if (store.overallStats.value) {
        store.overallStats.value.subfields.forEach(subfield => {
            dataSeries.push(subfield.subfield.name)
        });
    }

    return dataSeries;
}

/**
 * A helper method that configures
 * the overall stat data into 
 * chart data
 * @param No parameters expected
 * @returns An object with the appropriate chart data
 */
export const getChartData = () => {
    if (store.overallStats.value) {
        const generatedColors = getArrayOfHexadecimalColors(store.overallStats.value.subfields.length);
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
            },
            customBarChartData: {
                subfield_currYear: {
                    series: [{ data: getCustomBarChartDataSeriesForSubfieldCurrYear() }],
                    chartOptions: {
                        chart: {
                            type: 'bar',
                            height: 380
                        },
                        plotOptions: {
                            bar: {
                            barHeight: '100%',
                            distributed: true,
                            horizontal: true,
                            dataLabels: {
                                position: 'bottom'
                            },
                            }
                        },
                        colors: generatedColors,
                        dataLabels: {
                            enabled: true,
                            textAnchor: 'start',
                            style: {
                            colors: ['#fff']
                            },
                            formatter: function (val: any, opt: any) {
                            return opt.w.globals.labels[opt.dataPointIndex] + ":  " + val
                            },
                            offsetX: 0,
                            dropShadow: {
                            enabled: true
                            }
                        },
                        stroke: {
                            width: 1,
                            colors: ['#fff']
                        },
                        xaxis: {
                            categories: getCustomBarChartXAxisCategoriesForSubfields()
                        },
                        yaxis: {
                            labels: {
                            show: false
                            }
                        },
                        title: {
                            text: 'AI Researchers by subfield (Current Year)',
                            align: 'center',
                            floating: true
                        },
                        tooltip: {
                            theme: 'dark',
                            x: {
                                show: false
                            },
                            y: {
                                title: {
                                    formatter: function () {
                                        return ''
                                    }
                                }
                            }
                        }
                    }
                },
                subfield_previousYear: {
                    series: [{ data: getCustomBarChartDataSeriesForSubfieldPrevYear() }],
                    chartOptions: {
                        chart: {
                            type: 'bar',
                            height: 380
                        },
                        plotOptions: {
                            bar: {
                            barHeight: '100%',
                            distributed: true,
                            horizontal: true,
                            dataLabels: {
                                position: 'bottom'
                            },
                            }
                        },
                        colors: generatedColors,
                        dataLabels: {
                            enabled: true,
                            textAnchor: 'start',
                            style: {
                            colors: ['#fff']
                            },
                            formatter: function (val: any, opt: any) {
                            return opt.w.globals.labels[opt.dataPointIndex] + ":  " + val
                            },
                            offsetX: 0,
                            dropShadow: {
                            enabled: true
                            }
                        },
                        stroke: {
                            width: 1,
                            colors: ['#fff']
                        },
                        xaxis: {
                            categories: getCustomBarChartXAxisCategoriesForSubfields()
                        },
                        yaxis: {
                            labels: {
                            show: false
                            }
                        },
                        title: {
                            text: 'AI Researchers by subfield (Previous Year)',
                            align: 'center',
                            floating: true
                        },
                        tooltip: {
                            theme: 'dark',
                            x: {
                                show: false
                            },
                            y: {
                                title: {
                                    formatter: function () {
                                        return ''
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}