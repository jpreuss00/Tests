package io.ideas.engineering.ioc;

import io.ideas.engineering.ioc.api.KeyValueController;
import io.ideas.engineering.ioc.logic.KeyValueService;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class KeyValueControllerTest {

    private KeyValueService keyValueService;
    private KeyValueController keyValueController;
    private String target;
    private Request baserequest;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String KEY_PARAM;
    private String VALUE_PARAM;

    @Before
    public void setUp() throws Exception {
        this.KEY_PARAM = "key";
        this.VALUE_PARAM = "value";
        this.baserequest = Mockito.mock(Request.class);
        this.request = Mockito.mock(HttpServletRequest.class);
        this.response = Mockito.mock(HttpServletResponse.class);
        this.keyValueService = Mockito.mock(KeyValueService.class);
        this.keyValueController = new KeyValueController(keyValueService);
    }

    @Test
    public void handle_shouldPrint_Value_onGet() throws IOException {
        Mockito.doReturn("GET").when(request).getMethod();
        Mockito.doReturn("key").when(request).getParameter(KEY_PARAM);
        Mockito.doReturn("value").when(keyValueService).fetchValue("key");

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.doReturn(writer).when(response).getWriter();

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(writer).print("value");
        Mockito.verify(writer).flush();
    }

    @Test
    public void handle_shouldPersist_Value_onPut() throws IOException {
        Mockito.doReturn("PUT").when(request).getMethod();

        Mockito.doReturn("key").when(request).getParameter(KEY_PARAM);
        Mockito.doReturn("value").when(request).getParameter(VALUE_PARAM);

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(keyValueService).persistValue("key", "value");
    }

    @Test
    public void handle_shouldPersist_Value_onPost() throws IOException {
        Mockito.doReturn("POST").when(request).getMethod();

        Mockito.doReturn("key").when(request).getParameter(KEY_PARAM);
        Mockito.doReturn("value").when(request).getParameter(VALUE_PARAM);

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(keyValueService).persistValue("key", "value");
    }

    @Test
    public void handle_shouldDelete_Value_onDelete() throws IOException {
        Mockito.doReturn("DELETE").when(request).getMethod();

        Mockito.doReturn("key").when(request).getParameter(KEY_PARAM);

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(keyValueService).deleteValue("key");
    }

    @Test
    public void handle_shouldSet_Status_onDelete() throws IOException {
        Mockito.doReturn("DELETE").when(request).getMethod();

        Mockito.doReturn("key").when(request).getParameter(KEY_PARAM);

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(response).setStatus(HttpStatus.NO_CONTENT_204);
    }

    @Test
    public void handle_shouldCatch_IllegalArgument() throws IOException {
        Mockito.doThrow(IllegalArgumentException.class).when(request).getMethod();

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(response).setStatus(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void handle_shouldCatch_NoSuchElement() throws IOException {
        Mockito.doThrow(NoSuchElementException.class).when(request).getMethod();

        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(response).setStatus(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void handle_shouldSet_handled_true() throws IOException {
        keyValueController.handle(target, baserequest, request, response);

        Mockito.verify(baserequest).setHandled(true);
    }
}
