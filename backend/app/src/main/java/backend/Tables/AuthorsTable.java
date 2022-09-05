package backend.Tables;

import java.util.ArrayList;
import java.util.List;

import backend.DatabaseModels.Author;

public class AuthorsTable {
    public void insertAuthor(Author author){

    }

    public void updateAuthor(String authorId, Author author){  //is author needed if id is given?
        
    }

    public void deleteAuthor(String authorId){

    }

    public List<Author> listAll()
    {
        List<Author> authors = new ArrayList<Author>();
        return authors;
    }
}
