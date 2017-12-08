package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;


@Decorator
@Priority(1000)
public class ScrambledBookSource implements BookSource {

    @Inject
    @Delegate
    @Any
    BookSource bookSource; //dekorowany obiekt

    List<Book> books;

    @Override
    public List<Book> getBooks() {
        if (books == null) {
            books = bookSource.getBooks();
            Collections.shuffle(books);
        }

        return books;
    }
}
