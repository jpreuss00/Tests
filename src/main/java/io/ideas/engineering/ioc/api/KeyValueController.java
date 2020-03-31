package io.ideas.engineering.ioc.api;

import io.ideas.engineering.ioc.logic.KeyValueService;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

@Controller
public class KeyValueController extends AbstractHandler {

    private static final String KEY_PARAM = "key";
    private static final String VALUE_PARAM = "value";

    private final KeyValueService keyValueService;

    @Autowired
    public KeyValueController(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                final String value = this.keyValueService.fetchValue(request.getParameter(KEY_PARAM));
                final PrintWriter writer = response.getWriter();
                writer.print(value);
                writer.flush();
            }

            if ("PUT".equalsIgnoreCase(request.getMethod()) || "POST".equalsIgnoreCase(request.getMethod())) {
                this.keyValueService.persistValue(request.getParameter(KEY_PARAM), request.getParameter(VALUE_PARAM));
            }

            if ("DELETE".equalsIgnoreCase(request.getMethod())) {
                this.keyValueService.deleteValue(request.getParameter(KEY_PARAM));
                response.setStatus(HttpStatus.NO_CONTENT_204);
            }

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.BAD_REQUEST_400);
        } catch (NoSuchElementException e) {
            response.setStatus(HttpStatus.NOT_FOUND_404);
        } finally {
            baseRequest.setHandled(true); // important!
        }
    }
}
