package pl.gda.pg.eti.kask.javaee.enterprise.web.view.elfs;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Bow;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Forest;
import pl.gda.pg.eti.kask.javaee.enterprise.forest.ForestService;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ConversationScoped
public class EditElf implements Serializable {

    @EJB
    private ForestService forestService;

    @Inject
    AuthContext authContext;

    @Inject
    Conversation conversation;

    @Getter
    @Setter
    private Elf elf;

    @Getter
    @Setter
    private Forest forest;

    @Getter
    List<Forest> availableForests;

    @PostConstruct
    public void init() {
        availableForests = forestService.findAllForest();
        conversation.begin();
    }

    public void preRenderView() {
        if (elf == null) {
            elf = new Elf();
           // elf.setOwner(authContext.getCurrentUser());
        }
    }

    private List<SelectItem> forestsAsSelectItem;

    private List<SelectItem> typeBowAsSelectItem;

    public List<SelectItem> getForestsAsSelectItem() {
        if (forestsAsSelectItem == null) {
            forestsAsSelectItem = new ArrayList<>();
            for (Forest forest : forestService.findAllForest()) {
                forestsAsSelectItem.add(new SelectItem(forest, String.valueOf(forest.getId())));
            }
        }
        return forestsAsSelectItem;
    }

    public List<SelectItem> getTypeBowAsSelectItem() {
        if (typeBowAsSelectItem == null) {
            typeBowAsSelectItem = new ArrayList<>();
            for (Enum typeBow : Bow.values()) {
                typeBowAsSelectItem.add(new SelectItem(typeBow, typeBow.toString()));
            }
        }
        return typeBowAsSelectItem;
    }

    public String saveElf() {
       conversation.end();
       forestService.saveElf(elf);
       return "list?faces-redirect=true";
    }

    public String cancel() {
        conversation.end();
        return "/elfs/list.xhtml?faces-redirect=true";
    }

    public String next() {
        return "/elfs/edit_elf_2.xhtml";
    }

    public String back() {
        return "/elfs/edit_elf_1.xhtml";
    }
}
