package services;

import com.avaje.ebean.*;
import model.GrapthPrecisionCreateOrder;
import model.ReportPrecisionCreateOrder;
import play.mvc.PathBindable;
import scala.Function1;
import scala.util.Either;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Zhukov on 06.08.2016.
 */
public class ReportService {


    public List<ReportPrecisionCreateOrder> precisionCreateOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){
        return ReportPrecisionCreateOrder.find.where().eq("site",site).between("datePlanShip",dateBegin,dateEnd).isNotNull("customer").findList();
    }

    public List<GrapthPrecisionCreateOrder> precisionCreateOrdersGrapth(LocalDate dateBegin, LocalDate dateEnd, String site, String mode,String groupField){

        String sqlAll = "SELECT DISTINCT deviation,monthActualShip as monthActualShip,customer,procent FROM (" +
                        "SELECT p.dif_ as deviation,p.dat_ as monthActualShip,\'\' as customer,\n" +
                        " CAST(100.0* COUNT(p.id) OVER(PARTITION BY p.dat_,p.dif_)/COUNT(p.id) OVER(PARTITION BY p.dat_) AS NUMERIC(15,2)) as procent \n" +
                        " FROM (SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,\n" +
                        " DATEDIFF(DAY,r.DatePlan_Ship,r.datePlan_Mnfg)AS t, CAST(CONVERT(CHAR(6),DatePlan_Ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r\n" +
                        " WHERE r.site = :site AND r.DatePlan_Ship BETWEEN :dateBegin AND :dateEnd) AS p) as allRecord\n";

        String sqlClientGroup = "SELECT DISTINCT deviation,monthActualShip as monthActualShip,customer,procent FROM (SELECT p.dif_ as deviation,p.dat_ as monthActualShip,cust_name as customer,\n" +
                                "CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_,p.dif_)/COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_) AS NUMERIC(15,2)) AS procent \n" +
                                " FROM( SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,\n" +
                                " DATEDIFF(DAY,r.DatePlan_Ship,r.datePlan_Mnfg)AS t,\n" +
                                " CAST(CONVERT(CHAR(6),DatePlan_Ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_R PT_LOGIST r\n" +
                                " WHERE r.site = :site AND r.DatePlan_Ship BETWEEN :dateBegin AND :dateEnd) AS p) as allRecord \n";

        String sql=sqlAll;
        if(groupField.equals("\'customer\'") ) {
            sql = sqlClientGroup;
        }

        //SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        RawSql rawSql =  RawSqlBuilder
                .parse(sql)
               /* .columnMapping("deviation", "deviation")
                .columnMapping("dateBeginProduction", "dateBeginProduction")
                .columnMapping("customer", "customer")
                .columnMapping("procent", "procent")*/
                .create();

        List<GrapthPrecisionCreateOrder> precisionCreateOrders =  Ebean.find(GrapthPrecisionCreateOrder.class).setRawSql(rawSql).setParameter("site", site).setParameter("dateBegin", dateBegin).setParameter("dateEnd", dateEnd).findList();

        /*sqlQuery.setParameter("site", site);
        sqlQuery.setParameter("dateBegin", dateBegin);
        sqlQuery.setParameter("dateEnd", dateEnd);*/


        /*List<SqlRow> sqlRows =  sqlQuery.findList();*/
        return precisionCreateOrders;
    }

}
