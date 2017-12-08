package pl.gda.pg.eti.kask.javaee.enterprise.web.view;

import pl.gda.pg.eti.kask.javaee.enterprise.web.JSFUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named
@RequestScoped
public class Main implements Serializable {

    public String logout() throws ServletException {
        HttpServletRequest request = JSFUtils.getRequest();
        request.logout();
        request.getSession().invalidate();
        return "/index.xhtml";
    }
}
