package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString(exclude = "books")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = Author.Queries.FIND_ALL, query = "SELECT a FROM Author a"),
        @NamedQuery(name = Author.Queries.COUNT_ALL, query = "SELECT COUNT(a) FROM Author a"),
})
public class Author extends Audit implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Author.findAll";
        public static final String COUNT_ALL = "Author.countAll";
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
    private String surname;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
