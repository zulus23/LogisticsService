import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.Application;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import services.ReportService;

import javax.inject.Inject;

import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class ApplicationTest {

    @Inject
    ReportService reportService;

    @Before
    public void setupEbean(){
        String serverName = "default";

      Application application =  Helpers.fakeApplication();

        EbeanServer server = Ebean.getServer(serverName);
        ServerConfig config = new ServerConfig();
    }


    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        reportService = new ReportService();

    }


}
