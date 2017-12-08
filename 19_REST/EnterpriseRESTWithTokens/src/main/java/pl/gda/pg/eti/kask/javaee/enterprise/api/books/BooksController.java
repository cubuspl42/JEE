package pl.gda.pg.eti.kask.javaee.enterprise.api.books;

import pl.gda.pg.eti.kask.javaee.enterprise.api.auth.filter.Authorize;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Path("books")
@Produces(APPLICATION_JSON)
@Stateless
public class BooksController {

    @Inject
    BookService bookService;

    @GET
    @Path("/{id:[0-9]+}")
    @Authorize
    public Response getBookById(@PathParam("id") Integer id) {
        Book book = bookService.findBook(id);
        return book != null ? ok(book).build() : status(NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @Authorize
    public Response deleteBook(@PathParam("id") Integer id) {
        Book book = bookService.findBook(id);

        if (book != null) {
            bookService.removeBook(book);
            return ok().build();
        }

        return status(NOT_FOUND).build();
    }
}
