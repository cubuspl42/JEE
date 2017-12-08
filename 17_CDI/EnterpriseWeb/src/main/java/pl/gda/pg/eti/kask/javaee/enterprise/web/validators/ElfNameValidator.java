package pl.gda.pg.eti.kask.javaee.enterprise.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@ManagedBean
@RequestScoped
public class ElfNameValidator implements Validator {

    private static final String PATTERN = "^\\p{Lu}(\\p{L}|\\p{N})*([ ](\\p{L}|\\p{N})*)*$";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o instanceof String) {
            String name = (String) o;
            if (!Pattern.matches(PATTERN, name)) {
                throw new ValidatorException(new FacesMessage("Niepoprawne imię"));
            }
        } else  {
            throw new ValidatorException(new FacesMessage("Zły obiekt"));
        }
    }

}
