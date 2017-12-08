package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListAuthors implements Serializable {

    @EJB
    BookService bookService;

    @Getter
    private List<Author> authors;

    @PostConstruct
    public void init() {
        authors = bookService.findAllAuthors();
    }
}
