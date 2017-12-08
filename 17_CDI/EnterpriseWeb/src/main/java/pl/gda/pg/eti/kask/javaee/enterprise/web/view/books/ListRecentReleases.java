package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.events.BookEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ListRecentReleases implements Serializable {
    @EJB
    BookService bookService;

    @Getter
    List<Book> books;

    @PostConstruct
    public void init() {
        books = bookService.findRecentBooks(3);
    }

    public void reloadBooks(@Observes BookEvent bookEvent) {
        init();
    }
}
