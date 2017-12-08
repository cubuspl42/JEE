package pl.gda.pg.eti.kask.javaee.enterprise.api.books;

import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Collections.singletonList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.*;
import static pl.gda.pg.eti.kask.javaee.enterprise.api.ControllerUtils.okOrNotFound;
import static pl.gda.pg.eti.kask.javaee.enterprise.api.ControllerUtils.uri;

@Path("authors")
@Produces(APPLICATION_JSON)
@RequestScoped
public class AuthorsController {
    @Inject
    BookService bookService;

    @Inject
    UserService userService;

    @GET
    public List<Author> getAuthors() {
        return bookService.findAllAuthors();
    }

    @GET
    @Path("{id}")
    public Response getAuthorById(@PathParam("id") Integer id) {
        Author author = bookService.findAuthor(id);
        return okOrNotFound(author);
    }

    @GET
    @Path("{id}/books")
    public Response getAuthorBooks(@PathParam("id") Integer id) {
        Author author = bookService.findAuthor(id);

        return author != null ?
                ok(author.getBooks()).build() :
                status(NOT_FOUND).build();
    }

    @POST
    @Path("{id}/books")
    public Response saveAuthorBook(@PathParam("id") Integer authorId, Book book) {
        Author author = bookService.findAuthor(authorId);

        if (author == null) {
            return status(NOT_FOUND).build();
        }

        book.setAuthors(singletonList(author));
        book.setOwner(userService.findCurrentUser());

        bookService.saveBook(book);

        return created(uri(BooksController.class, "getBookById", book.getId())).build();
    }
}
