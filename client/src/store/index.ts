import { ref } from "vue";
import { Author } from "../core";

/**
 * Bundle and expose variables/functionalities 
 * to do with the managing of authors in the system
 */
 function initAuthors() {
    const authors = ref<Author[]>([]);
  
    function setAuthors(list: Author[]) {
        authors.value = list;
    }
  
    return {
        setAuthors,
        authors,
    };
}

const store = {
    ...initAuthors(),
};
  
export default store;