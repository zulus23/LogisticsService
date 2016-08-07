package services;

import model.ReportPrecisionCreateOrder;
import play.mvc.PathBindable;
import scala.Function1;
import scala.util.Either;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Zhukov on 06.08.2016.
 */
public class ReportService {

    public List<ReportPrecisionCreateOrder> precisionCreateOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){
        return ReportPrecisionCreateOrder.find.where().eq("site",site).between("dataCreateOrder",dateBegin,dateEnd).findList();
    }
}
