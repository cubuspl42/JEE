package pl.gda.pg.eti.kask.javaee.enterprise.web.view.elfs;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Forest;
import pl.gda.pg.eti.kask.javaee.enterprise.events.ElfEvent;
import pl.gda.pg.eti.kask.javaee.enterprise.forest.ForestService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class ListBestElfs implements ElfSource {
    @EJB
    ForestService forestService;

    @Getter
    List<Elf> elfs;

    @Getter
    List<Forest> forests;

    @PostConstruct
    public void init() { elfs = forestService.findBestElf(3); }

    public void handleElfEvent(@Observes ElfEvent elfEvent) {init();}

    //public void handleForestEvent(@Observes ForestEvent forestEventEvent) {init();}
}
