package pl.gda.pg.eti.kask.javaee.enterprise.forest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@ApplicationScoped
public class ForestProducer {
    @Produces
    @PersistenceContext(unitName = "JSFJPA_PU")
    private EntityManager em;
}
