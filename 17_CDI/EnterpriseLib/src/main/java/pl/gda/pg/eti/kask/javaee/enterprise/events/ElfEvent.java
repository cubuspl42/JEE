package pl.gda.pg.eti.kask.javaee.enterprise.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;

@AllArgsConstructor(staticName = "of")
public class ElfEvent {
    @Getter
    @Setter
    Elf elf;
}

