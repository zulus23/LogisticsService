package services;

import com.avaje.ebean.*;
import model.Deviation;
import model.GrapthPrecisionCreateOrder;
import model.ReportPrecisionCreateOrder;
import model.dto.OrderDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Zhukov on 06.08.2016.
 */
public class ReportService {


   /* public List<ReportPrecisionCreateOrder> precisionCreateOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){




        return find.where().eq("site",site).between("DateActual_Ship",dateBegin,dateEnd).isNotNull("customer").findList();
    }*/
   /*Отчет 1 все заказы*/
   public List<OrderDTO> allOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){


       List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

       List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());



       return listOrders;
   }

    /*Отчет 2 точность открытия заказов*/
    public List<OrderDTO> precisionCreateOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){


         List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

        List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO)

                                                            .collect(toList());


        listOrders.stream().forEach( e -> setDeviationCreateOrder(e,Integer.parseInt(mode)));


        return listOrders;
    }



    /*Отчет 3 точность планирования заказов*/
    public List<OrderDTO> precisionPlanOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){


        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

        List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationPlanOrder(e,Integer.parseInt(mode)));


        return listOrders;
    }


    /*  4 ----------------------- Точность постановки заказов в производство -------*/
    public List<OrderDTO> precisionProductionOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {
        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

        List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationProductionOrder(e,Integer.parseInt(mode)));


        return listOrders;


    }
    /*  5 ----------------------- Точность выходов заказов на склад -------*/
    public List<OrderDTO> precisionWhseOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {

        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

        List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationWhseOrder(e,Integer.parseInt(mode)));


        return listOrders;


    }
    /*  6 ----------------------- Точность отгрузки заказов -------*/
    public List<OrderDTO> precisionShipmentOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {
        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

        List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationShipmentOrder(e,Integer.parseInt(mode)));


        return listOrders;
    }
    /*  7 ----------------------- Точность доставки заказов -------*/
    public List<OrderDTO> precisionDeliveryOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {
        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site);

        List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationDeliveryOrder(e,Integer.parseInt(mode)));


        return listOrders;
    }

    private void setDeviationDeliveryOrder(OrderDTO orderDTO, int mode) {

        int tempDeviation = 0;
        switch (mode) {
            case 1: {
                /*Разница между датой поступления на склад и датой сдачи на склад*/
                /*TODO Нет даты поступления на склад*/
               /* if (orderDTO.getDateActualShip() != null && orderDTO.getDatePlanShip() != null) {
                    tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(), orderDTO.getDatePlanShip()).getDays();
                    tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                }
*/
                break;
            }
        }
    }


    private void setDeviationShipmentOrder(OrderDTO orderDTO, int mode) {
        int tempDeviation = 0;
        switch (mode) {
            case 1: {
                /*Разница между датой поступления на склад и датой сдачи на склад*/
                /*TODO Нет даты поступления на склад*/
                if (orderDTO.getDateActualShip() != null && orderDTO.getDatePlanShip() != null) {
                    tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(), orderDTO.getDatePlanShip()).getDays();
                    tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                }

                break;
            }
             /* Расчет даты по второму варианту*/
            case 2: {
                  /*TODO Нет даты поступления на склад*/
                int productionDeviation = 0;
                int whseDeviation = 0;
                 /* ----Взять отклонение с предыдущего шага -----*/
                if (orderDTO.getDateActualShip() != null && orderDTO.getDatePlanBeginProduction() != null) {

                    productionDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(), orderDTO.getDatePlanBeginProduction()).getDays();
                    productionDeviation = tempDeviation > 0 ? 0 : tempDeviation;
                }
                if (productionDeviation > 0) {
                    orderDTO.setDatePlanBeginProduction(orderDTO.getDatePlanBeginProduction().plusDays(productionDeviation));
                }

                if (orderDTO.getDatePlanBeginProduction() != null && orderDTO.getDatePlanWhse() != null) {
                    whseDeviation = Period.between(orderDTO.getDatePlanBeginProduction(), orderDTO.getDatePlanWhse()).getDays();
                    whseDeviation = whseDeviation > 0 ? 0 : whseDeviation;

                }
                if (whseDeviation > 0) {
                    orderDTO.setDateActualShip(Date.valueOf(orderDTO.getDateActualShip().toLocalDate().plusDays(whseDeviation)));
                }
                tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(), orderDTO.getDatePlanShip()).getDays();
                tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                break;
            }



        }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);

    }


    private void setDeviationWhseOrder(OrderDTO orderDTO,int mode) {
         int tempDeviation = 0;
         switch (mode) {
            case  1: {
                /*Разница между датой поступления на склад и датой сдачи на склад*/
                /*TODO Нет даты поступления на склад*/
                if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getDatePlanWhse() != null) {
                    tempDeviation = Period.between(orderDTO.getDatePlanBeginProduction(),orderDTO.getDatePlanWhse() ).getDays();
                    tempDeviation =   tempDeviation > 0 ? 0:tempDeviation ; ;
                }

                break;
             }
             /* Расчет даты по второму варианту*/
             case 2: {
                  /*TODO Нет даты поступления на склад*/
                 int productionDeviation =0;
                 /* ----Взять отклонение с предыдущего шага -----*/
                 if(orderDTO.getDateActualShip() != null && orderDTO.getDatePlanBeginProduction() != null) {

                     productionDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(),orderDTO.getDatePlanBeginProduction() ).getDays();
                     productionDeviation =   tempDeviation > 0 ? 0:tempDeviation ;
                 }
                 if(productionDeviation > 0 ) {
                     orderDTO.setDatePlanBeginProduction(orderDTO.getDatePlanBeginProduction().plusDays(productionDeviation));
                 }
                 /* --------------------------------------------------------------------------------------*/
                 if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getDatePlanWhse() != null) {
                     tempDeviation = Period.between(orderDTO.getDatePlanBeginProduction(),orderDTO.getDatePlanWhse() ).getDays();
                     tempDeviation =   tempDeviation > 0 ? 0:tempDeviation ;;
                 }

                 orderDTO.setDeviation(tempDeviation);
                 setCalcStatusNameByDeviation(orderDTO, tempDeviation);

                 break;
             }
         }

     }


    private void setDeviationCreateOrder(OrderDTO orderDTO,int mode ) {
        /* Расчет отклонения */
        int tempDeviation = 0;
        if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getDateCreateOrder() != null) {

            tempDeviation = Period.between(orderDTO.getDateCreateOrder(),orderDTO.getDatePlanBeginProduction() ).getDays();
            tempDeviation =   tempDeviation > 2 ? 0:tempDeviation ;
        }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);


    }
    /**/
    private void setDeviationPlanOrder(OrderDTO orderDTO,int mode ) {
        /* Расчет отклонения */
        int tempDeviation = 0;
        if(orderDTO.getDateActualShip() != null && orderDTO.getDatePlanShip() != null) {

            tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(),orderDTO.getDatePlanShip() ).getDays();
            tempDeviation =   tempDeviation > 0 ? 0:tempDeviation ;
        }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);


    }
    private void setDeviationProductionOrder(OrderDTO orderDTO,int mode ) {
        /* Расчет отклонения */
        int tempDeviation = 0;
        /*TODO нет даты начала производства в плане на сутки*/
        if(orderDTO.getDateActualShip() != null && orderDTO.getDatePlanBeginProduction() != null) {

            tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(),orderDTO.getDatePlanBeginProduction() ).getDays();
            tempDeviation =   tempDeviation > 0 ? 0:tempDeviation ;
        }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);


    }


    private void setCalcStatusNameByDeviation(OrderDTO orderDTO, int tempDeviation) {
        orderDTO.setCalcStatus(tempDeviation!=0 ? Deviation.YES.getName():Deviation.NO.getName());
    }

    private OrderDTO createOrderDTO(ReportPrecisionCreateOrder order){

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setOrderLine(order.getOrderLine());
        orderDTO.setCustomer(order.getCustomer());
        orderDTO.setManager(order.getManager());
        orderDTO.setItem(order.getItem());
        orderDTO.setItemDescription(order.getItemDescription());
        orderDTO.setDateActualShip(order.getMonthActualShip());
        orderDTO.setDateCreateOrder(order.getDateCreateOrder());
        orderDTO.setDatePlanBeginProduction(order.getDatePlanBeginProduction());
        orderDTO.setDatePlanShip(order.getDatePlanShip());
        orderDTO.setDatePlanWhse(order.getDatePlanWhse());
        orderDTO.setReasonDeviation(order.getReasonDeviation());
     /*   orderDTO.setDeviation(order.getDeviation());
        orderDTO.setCalcStatus(order.getCalcStatus());*/
       // orderDTO.setMonthShip(order.getMonthShip());
        //orderDTO.setDateCreateOrderFormat(order.getDateCreateOrderFormat());
        //orderDTO.setDateBeginProductionFormat(order.getDatePlanBeginProductionFormat());
       // orderDTO.setDatePlanToWhseFormat(order.getDatePlanWhseFormat());
       // orderDTO.setNornalizeGroupDate(order.getNornalizeGroupDate());


        return  orderDTO;
    }




    public List<GrapthPrecisionCreateOrder> precisionCreateOrdersGrapth(LocalDate dateBegin, LocalDate dateEnd, String site, String mode,String groupField){

        String sqlAll = "SELECT DISTINCT deviation,monthActualShip as monthActualShip,customer,procent FROM (" +
                        "SELECT p.dif_ as deviation,p.dat_ as monthActualShip,\'\' as customer,\n" +
                        " CAST(100.0* COUNT(p.id) OVER(PARTITION BY p.dat_,p.dif_)/COUNT(p.id) OVER(PARTITION BY p.dat_) AS NUMERIC(15,2)) as procent \n" +
                        " FROM (SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,\n" +
                        " DATEDIFF(DAY,r.DatePlan_Ship,r.datePlan_Mnfg)AS t, CAST(CONVERT(CHAR(6),DateActual_Ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r\n" +
                        " WHERE r.site = :site AND r.DateActual_Ship BETWEEN :dateBegin AND :dateEnd) AS p) as allRecord\n";

        String sqlClientGroup = "SELECT DISTINCT deviation,monthActualShip as monthActualShip,customer,procent FROM (SELECT p.dif_ as deviation,p.dat_ as monthActualShip,cust_name as customer,\n" +
                                "CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_,p.dif_)/COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_) AS NUMERIC(15,2)) AS procent \n" +
                                " FROM( SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,\n" +
                                " DATEDIFF(DAY,r.DatePlan_Ship,r.datePlan_Mnfg)AS t,\n" +
                                " CAST(CONVERT(CHAR(6),DateActual_Ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r\n" +
                                " WHERE r.site = :site AND r.DateActual_Ship BETWEEN :dateBegin AND :dateEnd) AS p) as allRecord \n";

        String sqlStatusGroup = "SELECT DISTINCT deviation,monthActualShip as monthActualShip,customer,procent FROM (SELECT p.dif_ as deviation,p.dat_ as monthActualShip,cust_name as customer,\n" +
                "CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_,p.dif_)/COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_) AS NUMERIC(15,2)) AS procent \n" +
                " FROM( SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,\n" +
                " DATEDIFF(DAY,r.DatePlan_Ship,r.datePlan_Mnfg)AS t,\n" +
                " CAST(CONVERT(CHAR(6),DateActual_Ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r\n" +
                " WHERE r.site = :site AND r.DateActual_Ship BETWEEN :dateBegin AND :dateEnd) AS p) as allRecord \n";



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

    private List<ReportPrecisionCreateOrder> getListOrders(LocalDate dateBegin, LocalDate dateEnd, String site) {
        return ReportPrecisionCreateOrder.find.where().eq("site",site).between("DateActual_Ship",dateBegin,dateEnd).isNotNull("customer").findList();
    }

}
