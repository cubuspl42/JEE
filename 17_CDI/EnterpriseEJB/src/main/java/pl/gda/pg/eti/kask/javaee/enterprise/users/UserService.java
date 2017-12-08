/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise.users;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;

    @Resource
    SessionContext sessionCtx;

    @RolesAllowed(User.Roles.ADMIN)
    public List<User> findAllUsers() {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
    }

    @RolesAllowed(User.Roles.ADMIN)
    public User findUser(String login) {
        return findUserByLogin(login);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    public User findCurrentUser() {
        String login = sessionCtx.getCallerPrincipal().getName();
        return findUserByLogin(login);
    }

    private User findUserByLogin(String login) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }
}
