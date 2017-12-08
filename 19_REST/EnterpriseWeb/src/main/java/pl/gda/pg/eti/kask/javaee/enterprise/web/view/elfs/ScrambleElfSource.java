package pl.gda.pg.eti.kask.javaee.enterprise.web.view.elfs;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Decorator
@Priority(1000)
public class ScrambleElfSource implements ElfSource {
    @Inject
    @Delegate
    @Any
    ElfSource elfSource;

    @Override
    public List<Elf> getElfs() {
        List<Elf> elfs = elfSource.getElfs();
        Collections.shuffle(elfs);
        return elfs;
    }
}
