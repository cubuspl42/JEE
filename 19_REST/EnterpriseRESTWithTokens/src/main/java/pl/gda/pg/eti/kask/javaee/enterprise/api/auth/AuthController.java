/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise.api.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Date;

import static java.time.ZonedDateTime.now;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

/**
 * @author stawrul
 */
@RequestScoped
@Path("")
public class AuthController {
    
    //BASE64(s3cr3t) = czNjcjN0
    public static final String SIGNING_KEY = "czNjcjN0";

    @Inject
    UserService userService;

    @POST
    @Path("/token")
    public Response issueToken(@FormParam("login") String login, @FormParam("password") String password,
                               @Context HttpServletRequest request) {
        try {
            request.login(login, password);
        } catch (ServletException e) {
            throw new NotAuthorizedException(e, "Form");
        }
        
        User user = userService.findCurrentUser();
        String token = buildJWT(user);

        return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
    }

    private String buildJWT(User user) {
        Date issuedAt = new Date();
        Date expirationAt = Date.from(now().plusMinutes(15).toInstant());

        String jwtToken = Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuer("Java EE Example Enterprise App")
                .setIssuedAt(issuedAt)
                .setExpiration(expirationAt)
                .claim("roles", user.getRoles())
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();

        return jwtToken;
    }

}
