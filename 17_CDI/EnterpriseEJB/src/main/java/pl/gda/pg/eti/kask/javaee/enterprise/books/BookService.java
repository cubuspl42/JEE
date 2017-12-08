package pl.gda.pg.eti.kask.javaee.enterprise.books;

import pl.gda.pg.eti.kask.javaee.enterprise.books.auth.OwnerOrAdminAllowed;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.events.BookEvent;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.BookCreation;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.BookDeletion;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class BookService implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Inject
    Event<BookEvent> bookEvent;


    @PermitAll
    public List<Book> findAllBooks() {
        return em.createNamedQuery(Book.Queries.FIND_ALL, Book.class).getResultList();
    }

    @PermitAll
    public List<Book> findRecentBooks(int limit) {
        TypedQuery<Book> query = em.createNamedQuery(Book.Queries.FIND_MOST_RECENT, Book.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @PermitAll
    public Book findBook(int id) {
        return em.find(Book.class, id);
    }

    @RolesAllowed(User.Roles.ADMIN)
    public void removeBook(Book book) {
        book = em.merge(book);
        em.remove(book);
        bookEvent.select(BookDeletion.Literal).fire(BookEvent.of(book));
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    @OwnerOrAdminAllowed
    public void saveBook(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            bookEvent.select(BookCreation.Literal).fire(BookEvent.of(book));
        } else {
            em.merge(book);
        }
    }

    @PermitAll
    public List<Author> findAllAuthors() {
        return em.createNamedQuery(Author.Queries.FIND_ALL, Author.class).getResultList();
    }

    @PermitAll
    public Author findAuthor(int id) {
        return em.find(Author.class, id);
    }
}
