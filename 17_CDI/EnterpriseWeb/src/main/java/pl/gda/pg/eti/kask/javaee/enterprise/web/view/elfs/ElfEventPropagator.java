package pl.gda.pg.eti.kask.javaee.enterprise.web.view.elfs;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import pl.gda.pg.eti.kask.javaee.enterprise.events.ElfEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ElfEventPropagator {
    @Inject
    @Push
    PushContext elfUpdates;

    public void handleElfEvent(@Observes ElfEvent elfEvent) { elfUpdates.send("update"); }
}
