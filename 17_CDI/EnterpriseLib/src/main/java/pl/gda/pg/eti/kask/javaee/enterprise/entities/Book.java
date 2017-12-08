package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.validators.InPast;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@ToString(of = "title")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = Book.Queries.FIND_ALL, query = "SELECT b FROM Book b ORDER BY b.creationDate"),
        @NamedQuery(name = Book.Queries.FIND_MOST_RECENT, query = "SELECT b FROM Book b ORDER BY b.creationDate DESC"),
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("book")
public class Book extends Audit implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Book.findAll";
        public static final String FIND_MOST_RECENT = "Book.findMostRecent";
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Cover cover = Cover.SOFT;

    @Getter
    @Setter
    @InPast(date = "2020-10-19")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @Getter
    @Setter
    @Size(min = 1)
    @ManyToMany(cascade = {MERGE, DETACH})
    private List<Author> authors = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne
    private User owner;

    public Book(String title, Cover cover, Date publishDate, List<Author> authors, User owner) {
        this.title = title;
        this.cover = cover;
        this.publishDate = publishDate;
        this.authors = authors;
        this.owner = owner;
    }
}
