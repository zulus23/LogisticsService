package controllers;

import model.GrapthPrecisionCreateOrder;
import model.ReportPrecisionCreateOrder;
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

import java.util.List;
import java.util.Map;


/**
 * Created by Zhukov on 05.08.2016.
 */
public class PrecisionOrderController extends Controller {

    private Form<ParamReportSelect> paramReportSelectForm;

    private List<ReportPrecisionCreateOrder> orderList;
    @Inject
    WebJarAssets webJarAssets;

    @Inject
    HelperForInterface helperForInterface;

    @Inject
    ReportService reportService;


    public PrecisionOrderController() {


    }




   /* public  Result listorder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode ) {

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
    }*/

    public  Result listAllOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode ) {

        /*LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

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
        String tempMode = "1";
        if(mode.isDefined()){
            tempMode = mode.get();
        }*/
        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();


        return ok(Json.toJson(reportService.allOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }


    public  Result listCreateOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode ) {

      /*  LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

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

         String tempMode = "1";
        if(mode.isDefined()){
          tempMode = mode.get();
        }
*/
        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();
        return ok(Json.toJson(reportService.precisionCreateOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }

    public  Result listPlanOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode ) {

       /* LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

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
        String tempMode = "1";
        if(mode.isDefined()){
            tempMode = mode.get();
        }

*/
        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();

        return ok(Json.toJson(reportService.precisionPlanOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }

    public  Result listProductionOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode) {
      /*  LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

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
        String tempMode = "1";
        if(mode.isDefined()){
            tempMode = mode.get();
        }*/

        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();
        return ok(Json.toJson(reportService.precisionProductionOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }
    public  Result listWhseOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode) {
       /* LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

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
        String tempMode = "1";
        if(mode.isDefined()){
            tempMode = mode.get();
        }*/
        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();
        return ok(Json.toJson(reportService.precisionWhseOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }
    public  Result listShipmentOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode) {
     /*   LocalDate tempdateBegin = LocalDate.now(),tempdateEnd = LocalDate.now();

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
        String tempMode = "1";
        if(mode.isDefined()){
            tempMode = mode.get();
        }*/
        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();
        return ok(Json.toJson(reportService.precisionShipmentOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }
    public  Result listDeliveryOrder(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode) {
        SelectParam selectParam = new SelectParam(dateBegin, dateEnd, site, mode).invoke();
        LocalDate tempdateBegin = selectParam.getTempdateBegin();
        LocalDate tempdateEnd = selectParam.getTempdateEnd();
        String tempSite = selectParam.getTempSite();
        String tempMode = selectParam.getTempMode();
        return ok(Json.toJson(reportService.precisionDeliveryOrders(tempdateBegin,tempdateEnd,tempSite,tempMode)));
    }


    public  Result show() {

       // return ok(views.html.precisionCreateOrder.render("",webJarAssets,paramReportSelectForm,null));
        return ok();
    }

    private class SelectParam {
        private Option<String> dateBegin;
        private Option<String> dateEnd;
        private Option<String> site;
        private Option<String> mode;
        private LocalDate tempdateBegin;
        private LocalDate tempdateEnd;
        private String tempSite;
        private String tempMode;

        public SelectParam(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode) {
            this.dateBegin = dateBegin;
            this.dateEnd = dateEnd;
            this.site = site;
            this.mode = mode;
        }

        public LocalDate getTempdateBegin() {
            return tempdateBegin;
        }

        public LocalDate getTempdateEnd() {
            return tempdateEnd;
        }

        public String getTempSite() {
            return tempSite;
        }

        public String getTempMode() {
            return tempMode;
        }

        public SelectParam invoke() {
            tempdateBegin = LocalDate.now();
            tempdateEnd = LocalDate.now();

            if(dateBegin.isDefined()){
                tempdateBegin = LocalDate.parse(dateBegin.get(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            if(dateEnd.isDefined()){
                tempdateEnd = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }

            tempSite = "";

            if(site.isDefined()){
                tempSite = helperForInterface.siteName(Integer.parseInt(site.get()));
            }
            tempMode = "1";
            if(mode.isDefined()){
                tempMode = mode.get();
            }
            return this;
        }
    }



}
