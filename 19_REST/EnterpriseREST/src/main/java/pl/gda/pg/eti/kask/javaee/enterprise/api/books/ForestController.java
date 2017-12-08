package pl.gda.pg.eti.kask.javaee.enterprise.api.books;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Forest;
import pl.gda.pg.eti.kask.javaee.enterprise.forest.ForestService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("forests")
@Produces(APPLICATION_JSON)
@RequestScoped
public class ForestController {

    @Inject
    ForestService forestService;

    @GET
    public List<Forest> getForests() {
        return forestService.findAllForest();
    }

    @GET
    @Path("/{id:[0-9]+}")
    public Forest getForestById(@PathParam("id") Integer id) {
        Forest forest = forestService.findForest(id);

        if (forest == null) {
            throw new NotFoundException();
        }

        return forest;
    }

    @PUT
    @Path("/{id:[0-9]+}")
    public Response updateForest(@PathParam("id") Integer id, Forest forest) {
//        forestService.mergeWithExisting(forest);
//        forestService.saveBook(forest);
        forestService.saveForest(forest);
        return Response.ok().build();
    }
}
