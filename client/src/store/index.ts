import { ref, computed } from "vue";
import { Author, AuthorProfile, AppMessage, User } from "../core/types";

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
    ...initAppMessage(),
    ...initAdmin(),
};
  
export default store;
