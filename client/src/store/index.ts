import { ref, computed } from "vue";
import { Author, AuthorProfile } from "../core/types";

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

const store = {
    ...initAuthors(),
};
  
export default store;
