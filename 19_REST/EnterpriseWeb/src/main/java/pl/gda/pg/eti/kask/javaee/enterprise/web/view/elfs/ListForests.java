package pl.gda.pg.eti.kask.javaee.enterprise.web.view.elfs;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Forest;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.forest.ForestService;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean
public class ListForests implements Serializable {

    @EJB
    private ForestService forestService;

    @Inject
    AuthContext authContext;

    @PostConstruct
    public void init() {
        forests = forestService.findAllForest();
    }

    @Getter
    private List<Forest> forests;

//    public List<Forest> getForests() {
//        if (forests == null) {
//            forests = forestService.findAllForest();
//        }
//        return forests;
//    }

    public void removeForest(Forest forest) {
        forestService.removeForest(forest);
        forests.remove(forest);
        //return "list?faces-redirect=true";
        init();
    }

    public void removeElf(Elf elf) {
        forestService.removeElf(elf);
        //forests.remove(elf);
        //return "list?faces-redirect=true";
        init();
    }

    public String reinforcement(int numberBow) {
        forestService.reinforcement(numberBow);
        return "list?faces-redirect=true";
    }

    public boolean canEdit(Forest forest) {
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                forest.getOwner().equals(authContext.getCurrentUser());
    }

}
