package pl.gda.pg.eti.kask.javaee.enterprise.api.books;

import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.notAcceptable;
import static javax.ws.rs.core.Response.ok;

@Path("translations")
@Produces({APPLICATION_JSON, APPLICATION_XML})
@RequestScoped
public class TranslationsController {

    @Inject
    BookService bookService;

    @GET
    public Response getTranslations(@Context Request request) {

        List<Variant> acceptableVariants = getAcceptableVariants();
        Variant variant = request.selectVariant(acceptableVariants);

        if (variant == null) {
            return notAcceptable(acceptableVariants).build();
        }

        String lang = variant.getLanguageString();
        List<Book> books = bookService.findAllBooks();

        List<String> translations = books.stream()
                .map(book -> format("Tytuł %s: %s", lang, translate(book.getTitle(), lang)))
                .collect(toList());

        return ok(translations).build();
    }

    private String translate(String title, String lang) {
        switch (lang) {
            case "pl":
                return title;
            case "en":
            default:
                return new StringBuilder(title).reverse().toString();
        }
    }

    private List<Variant> getAcceptableVariants() {
        return Variant
                .mediaTypes(
                        MediaType.APPLICATION_JSON_TYPE,
                        MediaType.APPLICATION_XML_TYPE)
                .languages(
                        Locale.ENGLISH,
                        Locale.forLanguageTag("pl"))
                .encodings(
                        "identity",
                        "gzip",
                        "deflate")
                .add().build();
    }
}
