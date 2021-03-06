package controllers;

import model.ReportPrecisionCreateOrder;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

import services.HelperForInterface;
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
    HelperForInterface helperForInterface;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.paramReportSelectForm = formFactory.form(ParamReportSelect.class);
    }

    @Inject
    WebJarAssets webJarAssets;

    public  Result runReport() {
        Result temp = ok();
        Form<ParamReportSelect> selectForm = paramReportSelectForm.bindFromRequest();
        ParamReportSelect paramReportSelect = selectForm.get();
//        List<ReportPrecisionCreateOrder> orderList = ReportPrecisionCreateOrder.find.all();
        switch (Integer.parseInt(paramReportSelect.getReport())){
            case 1: {
                temp = ok(views.html.allOrders.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
            case 2: {
                 temp = ok(views.html.precisionCreateOrder.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
            case 3: {
                 temp = ok(views.html.precisionPlaneOrder.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
            case 4: {
                temp = ok(views.html.precisionProductionOrder.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
            case 5: {
                temp = ok(views.html.precisionWhseOrder.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
            case 6: {
                temp = ok(views.html.precisionShipmentOrder.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
            case 7: {
                temp = ok(views.html.precisionDeliveryOrder.render("",webJarAssets,selectForm, paramReportSelect));
                break;
            }
        }




        return temp;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        paramReportSelectForm = paramReportSelectForm.fill(new ParamReportSelect());
        return ok(index.render("Your new application is ready.",webJarAssets,paramReportSelectForm));
    }

}
