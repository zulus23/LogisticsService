package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.ReportPrecisionCreateOrder;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Option;
import services.HelperForInterface;
import services.ReportService;
import views.html.*;


import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Created by Zhukov on 05.08.2016.
 */
public class PrecisionCreateOrderController extends Controller {

    private Form<ParamReportSelect> paramReportSelectForm;

    private List<ReportPrecisionCreateOrder> orderList;
    @Inject
    WebJarAssets webJarAssets;

    @Inject
    HelperForInterface helperForInterface;

    @Inject
    ReportService reportService;


    public PrecisionCreateOrderController() {

        orderList = ReportPrecisionCreateOrder.find.all();
        System.out.println(orderList);
    }

    public  Result listorder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode ) {

        LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

        if(dateBegin.isDefined()){
            tempdateBegin = LocalDate.parse(dateBegin.get(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        if(dateEnd.isDefined()){
            tempdateEnd = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        String tempSite = "";

        if(site.isDefined()){
           tempSite = helperForInterface.siteName(Integer.parseInt(site.get()));
        }



        return ok(Json.toJson(reportService.precisionCreateOrders(tempdateBegin,tempdateEnd,tempSite,"")));
    }

    public  Result show() {

        List<ReportPrecisionCreateOrder> orderList = ReportPrecisionCreateOrder.find.all();
        //JsonNode orderList = Json.toJson(ReportPrecisionCreateOrder.find.all());

        return ok(views.html.precisioncreateorder.render("",webJarAssets,paramReportSelectForm,null));
    }
}
