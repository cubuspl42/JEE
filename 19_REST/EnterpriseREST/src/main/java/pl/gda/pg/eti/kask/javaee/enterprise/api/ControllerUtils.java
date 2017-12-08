package pl.gda.pg.eti.kask.javaee.enterprise.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class ControllerUtils {

    public static Response okOrNotFound(Object entity) {
        if (entity != null) {
            return Response.ok(entity).build();
        }

        return Response.status(NOT_FOUND).build();
    }

    public static URI uri(Class<?> resource, String method, Object... values) {
        return UriBuilder.fromResource(resource).path(resource, method).build(values);
    }
}
