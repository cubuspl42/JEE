package pl.gda.pg.eti.kask.javaee.enterprise.web.view.elfs;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;

import java.io.Serializable;
import java.util.List;

public interface ElfSource extends Serializable {
    List<Elf> getElfs();
}
