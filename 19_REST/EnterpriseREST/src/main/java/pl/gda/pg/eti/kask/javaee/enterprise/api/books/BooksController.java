package pl.gda.pg.eti.kask.javaee.enterprise.api.books;

import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("books")
@Produces(APPLICATION_JSON)
@RequestScoped
public class BooksController {

    @Inject
    BookService bookService;

    @GET
    public List<Book> getBooks() {
        return bookService.findAllBooks();
    }

    @GET
    @Path("/{id:[0-9]+}")
    public Book getBookById(@PathParam("id") Integer id) {
        Book book = bookService.findBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        return book;
    }

    @PUT
    @Path("/{id:[0-9]+}")
    public Response updateBook(@PathParam("id") Integer id, Book book) {
        bookService.mergeWithExisting(book);
        bookService.saveBook(book);
        return Response.ok().build();
    }
}
