package pl.gda.pg.eti.kask.javaee.enterprise.api.users;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("users")
@Produces(APPLICATION_JSON)
@RequestScoped
public class UsersController {

    @Inject
    UserService userService;

    @GET
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GET
    @Path("/{login}")
    public User getUserById(@PathParam("login") String login) {
        return userService.findUser(login);
    }
}
