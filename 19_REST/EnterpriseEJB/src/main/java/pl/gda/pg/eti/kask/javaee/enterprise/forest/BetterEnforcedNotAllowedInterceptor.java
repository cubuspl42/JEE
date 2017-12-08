package pl.gda.pg.eti.kask.javaee.enterprise.forest;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Elf;

import javax.annotation.Priority;
import javax.ejb.AccessLocalException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@BetterEnforcedNotAllowed
@Priority(1000)
public class BetterEnforcedNotAllowedInterceptor implements Serializable {
    @Inject
    ForestService forestService;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];

        if(parameter instanceof Elf) {
            Elf elf = (Elf) parameter;

            int bestNumberArrow = forestService.findBestElf(1).get(0).getNumberArrows();

            if (elf.getNumberArrows() >= bestNumberArrow && elf.getId() == null) {
                elf.setNumberArrows(bestNumberArrow - 1);
            }

            return context.proceed();
        }

       throw new AccessLocalException("Client not authorized for this invocation");
    }
}
