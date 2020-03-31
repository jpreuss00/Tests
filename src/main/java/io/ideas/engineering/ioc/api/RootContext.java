package io.ideas.engineering.ioc.api;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootContext extends ContextHandler {

    @Autowired
    public RootContext(Handler keyValueController) {
        super("/");
        super.setHandler(keyValueController);
        super.setAllowNullPathInfo(true);
    }

}
