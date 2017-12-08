package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = User.Queries.FIND_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = User.Queries.FIND_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login")

})
public class User implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "User.findAll";
        public static final String FIND_BY_LOGIN = "User.findByLogin";
    }

    public static class Roles {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Column(nullable = false, unique = true)
    private String login;

    @Getter
    @Setter
    @JsonIgnore
    private String password;

    @Getter
    @Setter
    @ElementCollection
    private List<String> roles;

    public User(String login, String password, List<String> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }
}

