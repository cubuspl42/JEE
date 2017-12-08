package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="forests")
@NamedQueries({
        @NamedQuery(
                name = Forest.Queries.FIND_ALL,
                query = "SELECT f FROM Forest f"
        ),
        @NamedQuery(
                name = "findByName",
                query = "SELECT f FROM Forest f WHERE f.id = :id"
        ),
        @NamedQuery(
                name = Forest.Queries.REMOVE_BY_ID,
                query = "DELETE FROM Forest f WHERE f.id = :id"
        )
})
// Query query = session.getNamedQuery("findByName").setString("id", "7277");
public class Forest implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Forest.findAll";
        public static final String FIND_BY_NAME = "Forest.findByName";
        public static final String REMOVE_BY_ID = "Forest.removeById";
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private int numberTrees;

    @Getter
    @Setter
    @OneToMany(mappedBy = "forest", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Elf> elfs;

    @Getter
    @Setter
    @ManyToOne
    private User owner;

    public Forest(int numberTrees, User owner) {
        this.numberTrees = numberTrees;
        this.owner = owner;
    }

}
