package controllers;

import model.GrapthPrecisionCreateOrder;
import model.ReportPrecisionCreateOrder;
import model.dto.GraphDto;
import org.jetbrains.annotations.NotNull;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Option;
import services.HelperForInterface;
import services.ReportService;


import javax.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


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
    public  Result grapth(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode, Option<String> groupField ) {

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

        String tempGroup ="";
        if(site.isDefined()){
            tempGroup = groupField.get();
            System.out.println(tempGroup);
        }

        List<GrapthPrecisionCreateOrder> longMap = reportService.precisionCreateOrdersGrapth(tempdateBegin,tempdateEnd,tempSite,"",tempGroup).stream()
                                                 .filter(e -> e.getDeviation().equals("Без отклонений"))
                                                 .sorted((a,b) -> a.getMonthActualShip().compareTo(b.getMonthActualShip()))
                                                 .collect(toList());
       /* longMap.stream().forEach(e -> convertDate(e));

        Map<Date,List<Double>> dateListMap =  longMap.stream().collect(Collectors.groupingBy(GrapthPrecisionCreateOrder::getMonthActualShip,mapping(GrapthPrecisionCreateOrder::getProcent,toList())));

        List<GraphDto> graphDtos = dateListMap.entrySet().stream()
                .sorted((a,b)-> a.getKey().compareTo(b.getKey()))
                .peek(System.out::println)
                .map(e -> new GraphDto(e.getKey(), e.getValue().get(0))) //TODO необходимо убрать get(0)
                .collect(toList());*/
        //longMap.entrySet().stream().
        /*longMap.entrySet().stream()
                          .sorted((a,b)-> a.getKey().compareToIgnoreCase(b.getKey()))
                          .map(e -> new GrapthPrecisionCreateOrder(e.getValue().intValue(), getDateFrom(e)))
                                  //
                                   .forEach(e-> grapthPrecisionCreateOrders.add(e));
        /*forEach((k, v) -> {
              grapthPrecisionCreateOrders.add(new GrapthPrecisionCreateOrder(v.intValue(),k));
        });*/


        return ok(Json.toJson(longMap));

    }

    private void convertDate(GrapthPrecisionCreateOrder e) {
        Date temp =  e.getMonthActualShip();
        LocalDate localDate = temp.toLocalDate();
        e.setMonthActualShip(Date.valueOf(LocalDate.of(localDate.getYear(),localDate.getMonth(),1)));

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
