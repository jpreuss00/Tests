package io.ideas.engineering.ioc.infrastructure;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerConf {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server webServer(ContextHandler...contexts) {
        final int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8888"));
        final Server server = new Server(port);
        server.setHandler(new ContextHandlerCollection(contexts));
        return server;
    }
}
