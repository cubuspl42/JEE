package pl.gda.pg.eti.kask.javaee.enterprise.forest;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Forest;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.events.ElfEvent;
import pl.gda.pg.eti.kask.javaee.enterprise.events.ForestEvent;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;


@Stateless
public class ForestService implements Serializable {

    @Inject
    EntityManager em;

    @Resource
    SessionContext sessionCtx;

    @Inject
    Event<ElfEvent> elfEvent;

    @Inject
    Event<ForestEvent> forestEvent;

    @PermitAll
    public List<Forest> findAllForest() {
        return em.createNamedQuery(Forest.Queries.FIND_ALL, Forest.class).getResultList();
    }

    @PermitAll
    public Forest findForest(int forestId) {
        return em.find(Forest.class, forestId);
    }

    @PermitAll
    public Elf findElf(int elfId) {
        return em.find(Elf.class, elfId);
    }

    @PermitAll
    public List<Elf> findBestElf(int limit) {
        TypedQuery<Elf> query = em.createNamedQuery(Elf.Queries.FIND_BEST_ELF, Elf.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @PermitAll
    public void reinforcement(int numberBow) {
        em.createNamedQuery(Elf.Queries.REINFORCEMENT).setParameter("numberBow",numberBow).executeUpdate();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    public void saveForest(Forest forest) {
        if (forest.getId() == null) {
            em.persist(forest);
            forestEvent.select(ForestCreation.Literal).fire(ForestEvent.of(forest));
        } else {
            boolean isAdmin = sessionCtx.isCallerInRole(User.Roles.ADMIN);
            String login = sessionCtx.getCallerPrincipal().getName();
            boolean isOwner = forest.getOwner().getLogin().equals(login);

            if (isAdmin || isOwner) {
                em.merge(forest);
                forestEvent.select(ForestModification.Literal).fire(ForestEvent.of(forest));
            } else {
                throw new SecurityException("Insufficient permissions to edit the book");
            }
        }
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    @BetterEnforcedNotAllowed
    public void saveElf(Elf elf) {
        if (elf.getId() == null) {
            em.persist(elf);
            elfEvent.select(ElfCreation.Literal).fire(ElfEvent.of(elf));
        } else {
            boolean isAdmin = sessionCtx.isCallerInRole(User.Roles.ADMIN);
            String login = sessionCtx.getCallerPrincipal().getName();
            boolean isOwner = elf.getForest().getOwner().getLogin().equals(login);

            if (isAdmin || isOwner) {
                em.merge(elf);
                elfEvent.select(ElfModification.Literal).fire(ElfEvent.of(elf));
            } else {
                throw new SecurityException("Insufficient permissions to edit the book");
            }
        }
    }

    @RolesAllowed(User.Roles.ADMIN)
    public String removeForest(Forest forest) {
        em.remove(em.merge(forest));
        return "elfs/list.xhtml?faces-redirect=true";
    }

    @RolesAllowed(User.Roles.ADMIN)
    public String removeElf(Elf elf) {
        em.remove(em.merge(elf));
        elfEvent.select(ElfDeletion.Literal).fire(ElfEvent.of(elf));
        return "elfs/list.xhtml?faces-redirect=true";
    }

}
