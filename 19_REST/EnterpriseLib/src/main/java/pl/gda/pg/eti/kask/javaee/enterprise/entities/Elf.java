package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString(of = "name")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "elfs")
@NamedQueries({
        @NamedQuery(name = Elf.Queries.FIND_ALL, query = "SELECT e FROM Elf e"),
        @NamedQuery(name = Elf.Queries.COUNT_ALL, query = "SELECT COUNT(a) FROM Elf a"),
        @NamedQuery(name = Elf.Queries.REINFORCEMENT, query = "UPDATE Elf e SET e.numberArrows = e.numberArrows + :numberBow"),
        @NamedQuery(name = Elf.Queries.FIND_BEST_ELF, query = "SELECT e FROM Elf e ORDER BY e.numberArrows DESC"),
})
public class Elf implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Elf.findAll";
        public static final String COUNT_ALL = "Elf.countAll";
        public static final String REINFORCEMENT = "Elf.reinforcement";
        public static final String FIND_BEST_ELF = "Elf.findBestElf";
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int numberArrows;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Bow typeBow;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY) //cascade = {MERGE, DETACH}
    @JoinColumn(name = "forest_id", nullable = false)
    private Forest forest;

    public Elf(String name, int numberArrows, Bow typeBow, Forest forest) {
        this.name = name;
        this.numberArrows = numberArrows;
        this.typeBow = typeBow;
        this.forest = forest;
    }
}
