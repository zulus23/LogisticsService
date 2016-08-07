package controllers;

import model.ReportPrecisionCreateOrder;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private Form<ParamReportSelect> paramReportSelectForm;


    @Inject
    public HomeController(FormFactory formFactory) {
        this.paramReportSelectForm = formFactory.form(ParamReportSelect.class);
    }

    @Inject
    WebJarAssets webJarAssets;

    public  Result runReport() {

        Form<ParamReportSelect> selectForm = paramReportSelectForm.bindFromRequest();
        ParamReportSelect paramReportSelect = selectForm.get();
        List<ReportPrecisionCreateOrder> orderList = ReportPrecisionCreateOrder.find.all();
        Result temp = ok(precisioncreateorder.render("",webJarAssets,selectForm, paramReportSelect));

        return temp;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        paramReportSelectForm.fill(new ParamReportSelect(LocalDate.now().toString(),LocalDate.now().toString(),"1","1","1"));
        return ok(index.render("Your new application is ready.",webJarAssets,paramReportSelectForm));
    }

}
