package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.GrapthPrecisionCreateOrder;
import model.ReportPrecisionCreateOrder;
import org.jetbrains.annotations.NotNull;
import org.springframework.cglib.core.Local;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public  Result grapth(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode ) {

        List<GrapthPrecisionCreateOrder> grapthPrecisionCreateOrders = new ArrayList<>();

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

        Map<String,Long> longMap =  reportService.precisionCreateOrders(tempdateBegin,tempdateEnd,tempSite,"").stream()
                                                 .filter(e -> e.getMonthActualShip() != null)
                                                 .sorted((a,b) -> a.getMonthActualShip().compareTo(b.getMonthActualShip()))
                                                 .collect(Collectors.groupingBy(e-> String.format("%s-%s", e.getMonthIntShip(),e.getYearShip()),Collectors.counting()));

        longMap.entrySet().stream()
                          .sorted((a,b)-> a.getKey().compareToIgnoreCase(b.getKey()))
                          .map(e -> new GrapthPrecisionCreateOrder(e.getValue().intValue(), getDateFrom(e)))
                                  //
                                   .forEach(e-> grapthPrecisionCreateOrders.add(e));
        /*forEach((k, v) -> {
              grapthPrecisionCreateOrders.add(new GrapthPrecisionCreateOrder(v.intValue(),k));
        });*/


        return ok(Json.toJson(grapthPrecisionCreateOrders));

    }

    @NotNull
    private Date getDateFrom(Map.Entry<String, Long> e) {
        LocalDate localDate = LocalDate.of(Integer.parseInt(e.getKey().split("-")[1]),
                Integer.parseInt(e.getKey().split("-")[0]),1);
        Date temp = Date.valueOf (localDate);

        return temp;
    }


    public  Result show() {

       // List<ReportPrecisionCreateOrder> orderList = ReportPrecisionCreateOrder.find.all();
        //JsonNode orderList = Json.toJson(ReportPrecisionCreateOrder.find.all());

        return ok(views.html.precisioncreateorder.render("",webJarAssets,paramReportSelectForm,null));
    }
}
