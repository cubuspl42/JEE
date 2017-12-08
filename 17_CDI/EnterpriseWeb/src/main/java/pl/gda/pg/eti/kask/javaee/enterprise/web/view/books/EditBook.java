package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Comics;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Cover;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EditBook implements Serializable {

    @EJB
    BookService bookService;

    @Inject
    AuthContext authContext;

    @Getter
    @Setter
    private Book book;

    @Setter
    private boolean comics;

    @Getter
    List<Author> availableAuthors;

    @PostConstruct
    public void init() {
        availableAuthors = bookService.findAllAuthors();
    }

    public Cover[] getCoverTypes() {
        return Cover.values();
    }

    public void preRenderView() {
        if (book == null) {
            book = comics ? new Comics() : new Book();
            book.setOwner(authContext.getCurrentUser());
        }
    }

    public String saveBook() {
        bookService.saveBook(book);
        return "list_books?faces-redirect=true";
    }

    public boolean isComics() {
        return book instanceof Comics;
    }
}
