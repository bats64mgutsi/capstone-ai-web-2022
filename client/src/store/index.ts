import { ref, computed } from "vue";
import { 
    Author, 
    AuthorProfile, 
    InstitutionStat, 
    SubfieldStat, 
    CommunityStat, 
    AppMessage, 
    User,
 } from "../core/types";

/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of authors in the system
 */
 function initAuthors() {
    const authors = ref<Author[]>([]);
    const currentlySelectedAuthorId = ref("");
    const currentlySelectedAuthorProfile = ref<AuthorProfile>();
  
    function setAuthors(list: Author[]) {
        authors.value = list;
    }

    function setCurrentlySelectedAuthor(id: string) {
        currentlySelectedAuthorId.value = id;
    }
  
    const currentlySelectedAuthor = computed(() =>
      authors.value.find((p) => `${p.surname}` === currentlySelectedAuthorId.value)
    );

    function setCurrentlySelectedAuthorProfile(profile: AuthorProfile) {
        currentlySelectedAuthorProfile.value = profile;
    }
  
    return {
        setAuthors,
        setCurrentlySelectedAuthor,
        setCurrentlySelectedAuthorProfile,
        currentlySelectedAuthor,
        currentlySelectedAuthorId,
        currentlySelectedAuthorProfile,
        authors,
    };
}

/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of institution stats in the system
 */
 function initInstitutionStats() {
    const institutionStats = ref<InstitutionStat[]>([]);
    const currentlySelectedInstitutionStatId = ref<string>("");
  
    function setInstitutionStats(list: InstitutionStat[]) {
        institutionStats.value = list;
    }

    function setCurrentlySelectedInstitutionStat(id: string) {
        currentlySelectedInstitutionStatId.value = id;
    }
  
    const currentlySelectedInstitutionStat = computed(() =>
        institutionStats.value.find((i) => `${i.institution.id}` === currentlySelectedInstitutionStatId.value)
    );
  
    return {
        setInstitutionStats,
        setCurrentlySelectedInstitutionStat,
        currentlySelectedInstitutionStat,
        currentlySelectedInstitutionStatId,
        institutionStats,
    };
}

/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of subfield stats in the system
 */
 function initSubfieldStats() {
    const subfieldStats = ref<Array<SubfieldStat>>([]);
    const currentlySelectedSubfieldStatId = ref<string>("");
  
    function setSubfieldStats(list: SubfieldStat[]) {
        subfieldStats.value = list;
    }

    function setCurrentlySelectedSubfieldStat(id: string) {
        currentlySelectedSubfieldStatId.value = id;
    }
  
    const currentlySelectedSubfieldStat = computed(() =>
        subfieldStats.value.find((i) => `${i.id}` === currentlySelectedSubfieldStatId.value)
    );
  
    return {
        setSubfieldStats,
        setCurrentlySelectedSubfieldStat,
        currentlySelectedSubfieldStat,
        currentlySelectedSubfieldStatId,
        subfieldStats,
    };
}


/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of AI filters in the system
 */
 function initAIFilters() {
    const aiFilters = ref<string[]>([]);
    const currentlySelectedAIFilterId = ref<string>("");
  
    function setAIFilters(list: string[]) {
        aiFilters.value = list;
    }

    function setCurrentlySelectedAIFilter(id: string) {
        currentlySelectedAIFilterId.value = id;
    }
  
    const currentlySelectedAIFilter = computed(() =>
        aiFilters.value.find((i) => i === currentlySelectedAIFilterId.value)
    );
  
    return {
        setAIFilters,
        setCurrentlySelectedAIFilter,
        currentlySelectedAIFilter,
        currentlySelectedAIFilterId,
        aiFilters,
    };
}

/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of community stats in the system
 */
 function initCommunityStats() {
    const communityStats = ref<CommunityStat[]>([]);
    const currentlySelectedCommunityStatId = ref<string>("");
  
    function setCommunityStats(list: CommunityStat[]) {
        communityStats.value = list;
    }

    function setCurrentlySelectedCommunityStat(id: string) {
        currentlySelectedCommunityStatId.value = id;
    }
  
    const currentlySelectedCommunityStat = computed(() =>
        communityStats.value.find((i) => `${i.id}` === currentlySelectedCommunityStatId.value)
    );
  
    return {
        setCommunityStats,
        setCurrentlySelectedCommunityStat,
        currentlySelectedCommunityStat,
        currentlySelectedCommunityStatId,
        communityStats,
    };
}

/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of the currently logged-in admin
 */
 function initAdmin() {
    const admin = ref<User>();
  
    function setAdmin(user: User) {
        admin.value = user;
    }
  
    function unsetAdmin() {
        admin.value = undefined;
    }
  
    return {
        setAdmin,
        unsetAdmin,
        admin,
    };
}

/**
 * Bundle and expose variables/functionalities 
 * to do with app-wide message management
 */
 function initAppMessage() {
    const message = ref<AppMessage>({ type: "", message: "" });

    function setMessage(newMessage: AppMessage) {
        message.value = newMessage;
    }

    return {
        message,
        setMessage,
    };
}

const store = {
    ...initAuthors(),
    ...initInstitutionStats(),
    ...initSubfieldStats(),
    ...initAIFilters(),
    ...initCommunityStats(),
    ...initAppMessage(),
    ...initAdmin(),
};
  
export default store;
