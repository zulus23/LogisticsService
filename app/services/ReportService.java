package services;

import com.avaje.ebean.*;
import model.Deviation;
import model.ReportPrecisionCreateOrder;
import model.TypeReport;
import model.dto.*;
import services.report.AllOrder;

import javax.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by Zhukov on 06.08.2016.
 */
public class ReportService {

    @Inject
      HelperForInterface helperForInterface;

   /* public List<ReportPrecisionCreateOrder> precisionCreateOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){




        return find.where().eq("site",site).between("DateActual_Ship",dateBegin,dateEnd).isNotNull("customer").findList();
    }*/

   public IReport createReport(TypeReport typeReport,LocalDate dateBegin, LocalDate dateEnd, String site, String mode){

       IReport result = null;
       switch (typeReport.getId() ){
           case 1:{
               result = new AllOrder();
           }
       }

       return result;

   }



   /*Отчет 1 все заказы*/
   public List<OrderDTO> allOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){



       List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,1);

       List<OrderDTO> listOrders =   listAllOrders.stream().map(this::createOrderDTO).collect(toList());



       return listOrders;
   }

    /*Отчет 2 точность открытия заказов*/
    public List<PrecisionCreateOrderDTO> precisionCreateOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){


         List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,2);

        List<OrderDTO> listOrders =   listAllOrders.stream().filter(e -> e.getDatePlanBeginProduction() != null && e.getDateCreateOrder() != null).map(this::createOrderDTO)

                                                            .collect(toList());


        listOrders.stream().forEach( e -> {setDeviationCreateOrder(e,Integer.parseInt(mode)); e.setReasonDeviation("");});
        List<PrecisionCreateOrderDTO>  result = listOrders.stream().map(this::mapOrderToPrecisionCreateOrder).collect(toList());

        return result;
    }



    /*Отчет 3 точность планирования заказов*/
    public List<PrecisionPlanOrderDTO> precisionPlanOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode){


        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,3);

        List<OrderDTO> listOrders =   listAllOrders.stream().filter(e -> e.getPlanDeliveryDate() != null && e.getActualDeliveryDate() != null).map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> {setDeviationPlanOrder(e,Integer.parseInt(mode)); e.setReasonDeviation("");});
        List<PrecisionPlanOrderDTO>  result = listOrders.stream().map(this::mapOrderToPrecisionPlanOrder).collect(toList());

        return result;
    }


    /*  4 ----------------------- Точность постановки заказов в производство -------*/
    public List<PrecisionProductionOrderDTO> precisionProductionOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {
        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,4);

        List<OrderDTO> listOrders =   listAllOrders.stream().filter(e -> e.getDatePlanBeginProduction() != null && e.getFactProdReqDate() != null).map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationProductionOrder(e,Integer.parseInt(mode)));
        List<PrecisionProductionOrderDTO>  result = listOrders.stream().map(this::mapOrderToPrecisionProductionOrder).collect(toList());

        return result;


    }
    /*  5 ----------------------- Точность выходов заказов на склад -------*/
    public List<PrecisionWhseOrderDTO> precisionWhseOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {

        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,5);

        List<OrderDTO> listOrders =   listAllOrders.stream().filter(e -> e.getDatePlanWhse() != null && e.getFactOnWhseDate() != null).map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> {setDeviationWhseOrder(e,Integer.parseInt(mode));e.setReasonDeviation("");});

        List<PrecisionWhseOrderDTO>  result = listOrders.stream().map(this::mapOrderToPrecisionWhseOrder).collect(toList());
        return result;


    }
    /*  6 ----------------------- Точность отгрузки заказов -------*/
    public List<PrecisionShipOrderDTO> precisionShipmentOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {
        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,6);

        List<OrderDTO> listOrders =   listAllOrders.stream().filter(e -> e.getDatePlanShip() != null && e.getMonthActualShip() != null && !e.getTypeShip().equals("самовывоз"))
                                                            .map(this::createOrderDTO).collect(toList());

           listOrders.stream().forEach(e -> setDeviationShipmentOrder(e, Integer.parseInt(mode)));
           List<PrecisionShipOrderDTO>  result = listOrders.stream().map(this::mapOrderToPrecisionShipOrder).distinct().collect(toList());


        return result;
    }
    /*  7 ----------------------- Точность доставки заказов -------*/
    public List<PrecisionDeliveryOrderDTO> precisionDeliveryOrders(LocalDate dateBegin, LocalDate dateEnd, String site, String mode) {
        List<ReportPrecisionCreateOrder> listAllOrders = getListOrders(dateBegin, dateEnd, site,7);

        List<OrderDTO> listOrders =   listAllOrders.stream().filter(e -> e.getFactDeliveryDate() != null && e.getPlanDeliveryDate() != null && !e.getTypeShip().equals("самовывоз"))
                                                            .map(this::createOrderDTO).collect(toList());
        listOrders.stream().forEach( e -> setDeviationDeliveryOrder(e,Integer.parseInt(mode)));
        List<PrecisionDeliveryOrderDTO>  result = listOrders.stream().map(this::mapOrderToPrecisionDeliveryOrder).collect(toList());

        return result;
    }
    /* =================== Отклонение доставки заказа  =====================================*/
    private void setDeviationDeliveryOrder(OrderDTO orderDTO, int mode) {

        int tempDeviation = 0;
        switch (mode) {
            case 1: {
                orderDTO.setReasonDeviation("");

                if (orderDTO.getFactDeliveryDate() != null && orderDTO.getPlanDeliveryDate() != null) {
                    tempDeviation = Period.between(orderDTO.getFactDeliveryDate().toLocalDate(), orderDTO.getPlanDeliveryDate().toLocalDate()).getDays();
                    tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                }

                break;
            }
            case 2: {
                orderDTO.setReasonDeviation("");
                  /*TODO Нет даты поступления на склад*/
                int productionDeviation = 0;
                int whseDeviation = 0;
                int shipDeviation = 0;
                 /* ----Взять отклонение с предыдущего шага -----*/
                if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getFactProdReqDate() != null) {

                    productionDeviation = Period.between(orderDTO.getFactProdReqDate().toLocalDate(),orderDTO.getDatePlanBeginProduction() ).getDays();
                    productionDeviation =   productionDeviation > 0 ? 0:Math.abs(productionDeviation);
                }

                if(productionDeviation > 0 ) {
                     /*увеличить плановую дата поступления на склад*/
                    orderDTO.setDatePlanWhse(orderDTO.getDatePlanWhse().plusDays(productionDeviation));
                }

                if (orderDTO.getDatePlanWhse() != null && orderDTO.getFactOnWhseDate() != null) {
                    whseDeviation = Period.between(orderDTO.getDatePlanWhse(), orderDTO.getFactOnWhseDate().toLocalDate()).getDays();
                    whseDeviation = whseDeviation > 0 ? 0 : Math.abs(whseDeviation);

                }
                if (whseDeviation > 0) {
                    orderDTO.setDatePlanShip(orderDTO.getDatePlanShip().plusDays(whseDeviation));
                }

                if (orderDTO.getDateActualShip() != null && orderDTO.getDatePlanShip() != null) {
                    shipDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(), orderDTO.getDatePlanShip()).getDays();
                    shipDeviation = shipDeviation > 0 ? 0 : Math.abs(shipDeviation);
                }
                if (shipDeviation > 0) {
                    orderDTO.setPlanDeliveryDate(Date.valueOf(orderDTO.getPlanDeliveryDate().toLocalDate().plusDays(shipDeviation)));
                }


                tempDeviation = Period.between(orderDTO.getFactDeliveryDate().toLocalDate(), orderDTO.getPlanDeliveryDate().toLocalDate()).getDays();
                tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                break;
            }
            case 3:{

                if (orderDTO.getPlanDeliveryDate_M() != null) {
                    orderDTO.setPlanDeliveryDate(orderDTO.getPlanDeliveryDate_M());
                }

                if (orderDTO.getFactDeliveryDate() != null && orderDTO.getPlanDeliveryDate() != null) {
                    tempDeviation = Period.between(orderDTO.getFactDeliveryDate().toLocalDate(), orderDTO.getPlanDeliveryDate().toLocalDate()).getDays();
                    tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                }


                break;
            }
        }

        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);

    }

    /* =================== Отклонение отгрузки заказа =====================================*/
    private void setDeviationShipmentOrder(OrderDTO orderDTO, int mode) {
        int tempDeviation = 0;
        switch (mode) {
            case 1: {
                orderDTO.setReasonDeviation("");
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
                 orderDTO.setReasonDeviation("");
                  /*TODO Нет даты поступления на склад*/
                int productionDeviation = 0;
                int whseDeviation = 0;
                 /* ----Взять отклонение с предыдущего шага -----*/
                 if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getFactProdReqDate() != null) {
                            productionDeviation = Period.between(orderDTO.getFactProdReqDate().toLocalDate(),orderDTO.getDatePlanBeginProduction() ).getDays();
                            productionDeviation =   productionDeviation > 0 ? 0:Math.abs(productionDeviation);
                 }
                 if(productionDeviation > 0 ) {
                     /*увеличить плановую дата поступления на склад*/
                         orderDTO.setDatePlanWhse(orderDTO.getDatePlanWhse().plusDays(productionDeviation));
                 }

                if (orderDTO.getDatePlanWhse() != null && orderDTO.getFactOnWhseDate() != null) {
                    whseDeviation = Period.between( orderDTO.getFactOnWhseDate().toLocalDate(),orderDTO.getDatePlanWhse()).getDays();
                    whseDeviation = whseDeviation > 0 ? 0 : Math.abs(whseDeviation);

                }
                if (whseDeviation > 0) {
                    orderDTO.setDatePlanShip(orderDTO.getDatePlanShip().plusDays(whseDeviation));
                }
                tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(),orderDTO.getDatePlanShip()).getDays();
                tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                break;
            }
            case 3 : {
                if (Optional.ofNullable(orderDTO.getDatePlanShip_M()).isPresent()) {
                    orderDTO.setDatePlanShip(orderDTO.getDatePlanShip_M().toLocalDate());
                }
                if (orderDTO.getDateActualShip() != null && orderDTO.getDatePlanShip() != null) {
                    tempDeviation = Period.between(orderDTO.getDateActualShip().toLocalDate(), orderDTO.getDatePlanShip()).getDays();
                    tempDeviation = tempDeviation > 0 ? 0 : Math.abs(tempDeviation);
                }
                break;
            }


        }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);

    }

     /* =================== Отклонение выхода заказа на склад =====================================*/
    private void setDeviationWhseOrder(OrderDTO orderDTO,int mode) {
         int tempDeviation = 0;
         switch (mode) {
            case  1: {
                /*Разница между датой поступления на склад и датой сдачи на склад*/
                /*TODO Нет даты поступления на склад*/
                if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getDatePlanWhse() != null) {
                    tempDeviation = Period.between(orderDTO.getFactOnWhseDate().toLocalDate(),orderDTO.getDatePlanWhse()).getDays();
                    tempDeviation =   tempDeviation > 0 ? 0:Math.abs(tempDeviation) ; ;
                }

                break;
             }
             /* Расчет даты по второму варианту*/
             case 2: {
                  /*TODO Нет даты поступления на склад*/
                 int productionDeviation =0;
                 /* ----Взять отклонение с предыдущего шага -----*/
                 /*TODO Здесь необходимо заменить getDateActualShip() на  дату производства в плане на сутки*/
                 if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getFactProdReqDate() != null) {

                     productionDeviation = Period.between(orderDTO.getFactProdReqDate().toLocalDate(),orderDTO.getDatePlanBeginProduction() ).getDays();
                     productionDeviation =   productionDeviation > 0 ? 0:Math.abs(productionDeviation) ;
                 }

                 if(productionDeviation > 0 ) {
                     /*увеличить плановую дата поступления на склад*/
                     orderDTO.setDatePlanWhse(orderDTO.getDatePlanWhse().plusDays(productionDeviation));
                 }
                 /* --------------------------------------------------------------------------------------*/
                 /*TODO Здесь необходимо заменить getDateActualShip() на  дату сдачи на склад факт*/
                 if(orderDTO.getFactOnWhseDate() != null && orderDTO.getDatePlanWhse() != null) {
                     tempDeviation = Period.between(orderDTO.getFactOnWhseDate().toLocalDate(),orderDTO.getDatePlanWhse() ).getDays();
                     tempDeviation =   tempDeviation > 0 ? 0:Math.abs(tempDeviation);
                 }



                 break;
             }
             default: {
                /*Разница между датой поступления на склад и датой сдачи на склад*/
                /*TODO Нет даты поступления на склад*/
                 if(orderDTO.getDatePlanBeginProduction() != null && orderDTO.getDatePlanWhse() != null) {
                     tempDeviation = Period.between(orderDTO.getFactOnWhseDate().toLocalDate(),orderDTO.getDatePlanWhse()).getDays();
                     tempDeviation =   tempDeviation > 0 ? 0:Math.abs(tempDeviation) ; ;
                 }

                 break;
             }
         }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);

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
        if(orderDTO.getActualDeliveryDate() != null && orderDTO.getPlanDeliveryDate() != null) {

            tempDeviation = Period.between(orderDTO.getActualDeliveryDate().toLocalDate(),orderDTO.getPlanDeliveryDate().toLocalDate() ).getDays();
            tempDeviation =   tempDeviation > 0 ? 0:Math.abs(tempDeviation) ;
        }
        orderDTO.setDeviation(tempDeviation);
        setCalcStatusNameByDeviation(orderDTO, tempDeviation);


    }
    private void setDeviationProductionOrder(OrderDTO orderDTO,int mode ) {
        /* Расчет отклонения */
        int tempDeviation = 0;
        /*TODO нет даты начала производства в плане на сутки*/
        if(orderDTO.getFactProdReqDate() != null && orderDTO.getDatePlanBeginProduction() != null) {

            tempDeviation = Period.between(orderDTO.getFactProdReqDate().toLocalDate(),orderDTO.getDatePlanBeginProduction()).getDays();
            tempDeviation =   tempDeviation > 0 ? 0:Math.abs(tempDeviation) ;
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
        orderDTO.setDatePlanShipOriginal(order.getDatePlanShip());
        orderDTO.setDatePlanWhseOriginal(order.getDatePlanWhse());
        /*TODO необходмисо сохранить оригинальную дату доставки*/
        if(Optional.ofNullable(order.getPlanDeliveryDate()).isPresent()) {
            orderDTO.setDatePlanDeliveryOriginal(order.getPlanDeliveryDate().toLocalDate());
        }
        if(Optional.ofNullable(order.getDatePlanWhse()).isPresent()) {
           orderDTO.setDatePlanWhseOriginal(order.getDatePlanWhse());
        }
        if(Optional.ofNullable(order.getDatePlanShip()).isPresent()) {
         orderDTO.setDatePlanShipOriginal(order.getDatePlanShip());
        }

        orderDTO.setActualDeliveryDate(order.getActualDeliveryDate());
        orderDTO.setFactDeliveryDate(order.getFactDeliveryDate());
        orderDTO.setFactOnWhseDate(order.getFactOnWhseDate());
        orderDTO.setFactProdReqDate(order.getFactProdReqDate());
        orderDTO.setPlanDeliveryDate(order.getPlanDeliveryDate());
        orderDTO.setPlanProdReqDate(order.getPlanProdReqDate());

        orderDTO.setReasonDeviation(order.getReasonDeviation());
        orderDTO.setPlanDeliveryDate_M(order.getPlanDeliveryDate_M());
        orderDTO.setDatePlanShip_M(order.getDatePlanShip_M());
        orderDTO.setTypeShip(order.getTypeShip());
        orderDTO.setTypeCustomer(order.getTypeCustomer());


        return  orderDTO;
    }




    /*public List<GrapthPrecisionCreateOrder> precisionCreateOrdersGrapth(LocalDate dateBegin, LocalDate dateEnd, String site, String mode,String groupField){

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
               *//* .columnMapping("deviation", "deviation")
                .columnMapping("dateBeginProduction", "dateBeginProduction")
                .columnMapping("customer", "customer")
                .columnMapping("procent", "procent")*//*
                .create();

        List<GrapthPrecisionCreateOrder> precisionCreateOrders =  Ebean.find(GrapthPrecisionCreateOrder.class).setRawSql(rawSql).setParameter("site", site).setParameter("dateBegin", dateBegin).setParameter("dateEnd", dateEnd).findList();

        *//*sqlQuery.setParameter("site", site);
        sqlQuery.setParameter("dateBegin", dateBegin);
        sqlQuery.setParameter("dateEnd", dateEnd);*//*


        *//*List<SqlRow> sqlRows =  sqlQuery.findList();*//*
        return precisionCreateOrders;
    }*/

    private List<ReportPrecisionCreateOrder> getListOrders(LocalDate dateBegin, LocalDate dateEnd, String site,int typeReport) {
         return  selectData(dateBegin,dateEnd, site,typeReport);

    }

    private ReportPrecisionCreateOrder mapSqlRowToReportPrecisionOrder(SqlRow row){
        ReportPrecisionCreateOrder precisionOrder = new ReportPrecisionCreateOrder();
        precisionOrder.setId(row.getLong("id"));
        precisionOrder.setOrderNumber(row.getString("co_num"));
        precisionOrder.setOrderLine(row.getInteger("co_line").toString());
        precisionOrder.setCustomer(row.getString("Cust_name"));
        precisionOrder.setSite(row.getString("site"));
        precisionOrder.setManager(row.getString("FrontSlsmanName"));
        precisionOrder.setItem(row.getString("item"));
        precisionOrder.setItemDescription(row.getString("Item_Desc"));
        precisionOrder.setReasonDeviation(row.getString("StatusRow"));
        precisionOrder.setDateCreateOrder(row.getDate("DateCreate_Row").toLocalDate());
        if(row.getDate("DatePlan_Whse")!= null) {
            precisionOrder.setDatePlanWhse(row.getDate("DatePlan_Whse").toLocalDate());
        }
        if(row.getDate("DatePlan_Mnfg") != null) {
            precisionOrder.setDatePlanBeginProduction(row.getDate("DatePlan_Mnfg").toLocalDate());
        }
        if(row.getDate("DatePlan_Ship") != null) {
            precisionOrder.setDatePlanShip(row.getDate("DatePlan_Ship").toLocalDate());
        }
        precisionOrder.setMonthActualShip(row.getDate("DateActual_Ship"));
        precisionOrder.setPlanDeliveryDate(row.getDate("PlanDeliveryDate"));
        precisionOrder.setPlanProdReqDate(row.getDate("PlanProdReqDate"));
        precisionOrder.setFactDeliveryDate(row.getDate("FactDeliveryDate"));
        precisionOrder.setFactOnWhseDate(row.getDate("FactOnWhseDate"));
        precisionOrder.setFactProdReqDate(row.getDate("FactProdReqDate"));
        precisionOrder.setActualDeliveryDate(row.getDate("ActualDeliveryDate"));
        precisionOrder.setPlanDeliveryDate_M(row.getDate("PlanDeliveryDate_M"));
        precisionOrder.setDatePlanShip_M(row.getDate("DatePlan_Ship_M"));
        precisionOrder.setTypeShip(row.getString("ShipCodeDescr"));
        precisionOrder.setTypeCustomer(row.getString("CustStrateg"));

        return  precisionOrder;
    }



   public List<ReportPrecisionCreateOrder> selectData(LocalDate dateBegin, LocalDate dateEnd, String site,int typeReport){
        String sqlsp = "exec dbo.gtk_rpt_logist_www :v_startdate, :v_enddate, :v_site,:v_type_rep";
        List<ReportPrecisionCreateOrder> result =   Ebean.createSqlQuery(sqlsp).setParameter("v_startdate",dateBegin)
                .setParameter("v_enddate",dateEnd)
                .setParameter("v_site",site)
                .setParameter("v_type_rep",typeReport)
                .findList().stream().map(this::mapSqlRowToReportPrecisionOrder).collect(toList());

        return result;

    }

    /* ----------------------- Точность открытия заказов -----------------------*/

    private PrecisionCreateOrderDTO mapOrderToPrecisionCreateOrder(OrderDTO order){
        PrecisionCreateOrderDTO result = new PrecisionCreateOrderDTO();
        result.setId(order.getId());
        result.setOrderNumber(order.getOrderNumber());
        result.setOrderLine(order.getOrderLine());
        result.setCustomer(order.getCustomer());
        result.setManager(order.getManager());
        result.setItemDescription(order.getItemDescription());
        result.setItem(order.getItem());
        result.setMonthShip(order.getMonthShip());
        result.setDeviation(order.getDeviation());
        result.setReasonDeviation(order.getReasonDeviation());
        result.setCalcStatus(order.getCalcStatus());
        result.setDateActualShip(order.getDateActualShip());
        result.setNornalizeGroupDate(order.getNornalizeGroupDate());
        result.setDeviationHide(order.getDeviationHide());
        result.setTypeCustomer(order.getTypeCustomer());
        result.setDateCreateOrderFormat(order.getDateCreateOrderFormat());////
        result.setDatePlanBeginProductionFormat(order.getDatePlanBeginProductionFormat());


        return  result;
    }

    /* --------------------   Точность планирования заказов -------------------------------*/

    private PrecisionPlanOrderDTO mapOrderToPrecisionPlanOrder(OrderDTO order){
        PrecisionPlanOrderDTO result = new PrecisionPlanOrderDTO();
        result.setId(order.getId());
        result.setOrderNumber(order.getOrderNumber());
        result.setOrderLine(order.getOrderLine());
        result.setCustomer(order.getCustomer());
        result.setManager(order.getManager());
        result.setItemDescription(order.getItemDescription());
        result.setItem(order.getItem());
        result.setMonthShip(order.getMonthShip());
        result.setDeviation(order.getDeviation());
        result.setReasonDeviation(order.getReasonDeviation());
        result.setCalcStatus(order.getCalcStatus());
        result.setDateActualShip(order.getDateActualShip());
        result.setNornalizeGroupDate(order.getNornalizeGroupDate());
        result.setDeviationHide(order.getDeviationHide());
        result.setTypeCustomer(order.getTypeCustomer());
        result.setPlanDeliveryDateFormat(order.getPlanDeliveryDateFormat());////
        result.setActualDeliveryDateFormat(order.getActualDeliveryDateFormat());


        return  result;
    }

    /* -------------------- Точность постановки заказов в производство ---------------------*/


    private PrecisionProductionOrderDTO mapOrderToPrecisionProductionOrder(OrderDTO order){
        PrecisionProductionOrderDTO result = new PrecisionProductionOrderDTO();
        result.setId(order.getId());
        result.setOrderNumber(order.getOrderNumber());
        result.setOrderLine(order.getOrderLine());
        result.setCustomer(order.getCustomer());
        result.setManager(order.getManager());
        result.setItemDescription(order.getItemDescription());
        result.setItem(order.getItem());
        result.setMonthShip(order.getMonthShip());
        result.setDeviation(order.getDeviation());
        result.setReasonDeviation(order.getReasonDeviation());
        result.setCalcStatus(order.getCalcStatus());
        result.setDateActualShip(order.getDateActualShip());
        result.setNornalizeGroupDate(order.getNornalizeGroupDate());
        result.setDeviationHide(order.getDeviationHide());
        result.setTypeCustomer(order.getTypeCustomer());
        result.setFactProdReqDateFormat(order.getFactProdReqDateFormat());////
        result.setDatePlanBeginProductionFormat(order.getDatePlanBeginProductionFormat());


        return  result;
    }

    /* ---------------------  Точность отгрузки заказов  -----------------------------------*/
    private PrecisionShipOrderDTO mapOrderToPrecisionShipOrder(OrderDTO order){
        PrecisionShipOrderDTO result = new PrecisionShipOrderDTO();
            result.setId(order.getId());
            result.setOrderNumber(order.getOrderNumber());
            result.setOrderLine(order.getOrderLine());
            result.setCustomer(order.getCustomer());
            result.setManager(order.getManager());
            result.setItemDescription(order.getItemDescription());
            result.setItem(order.getItem());
            result.setMonthShip(order.getMonthShip());
            result.setDatePlanShipFormat(order.getDatePlanShipFormat());
            result.setDateActualShipFormat(order.getDateActualShipFormat());
            result.setDeviation(order.getDeviation());
            result.setReasonDeviation(order.getReasonDeviation());
            result.setCalcStatus(order.getCalcStatus());
            result.setDateActualShip(order.getDateActualShip());
            result.setNornalizeGroupDate(order.getNornalizeGroupDate());
            result.setDeviationHide(order.getDeviationHide());
            result.setChangeDatePlanShip(order.isChangeDatePlanShip());
            result.setTypeCustomer(order.getTypeCustomer());
        return  result;
    }

    /* ------------------------------ Точность выхода заказов на склад ---------------------------------*/
    private PrecisionWhseOrderDTO mapOrderToPrecisionWhseOrder(OrderDTO order){
        PrecisionWhseOrderDTO result = new PrecisionWhseOrderDTO();
        result.setId(order.getId());
        result.setOrderNumber(order.getOrderNumber());
        result.setOrderLine(order.getOrderLine());
        result.setCustomer(order.getCustomer());
        result.setManager(order.getManager());
        result.setItemDescription(order.getItemDescription());
        result.setItem(order.getItem());
        result.setMonthShip(order.getMonthShip());
        result.setDeviation(order.getDeviation());
        result.setReasonDeviation(order.getReasonDeviation());
        result.setCalcStatus(order.getCalcStatus());
        result.setDateActualShip(order.getDateActualShip());
        result.setNornalizeGroupDate(order.getNornalizeGroupDate());
        result.setDeviationHide(order.getDeviationHide());
        result.setTypeCustomer(order.getTypeCustomer());
        result.setFactOnWhseDateFormat(order.getFactOnWhseDateFormat());
        result.setDatePlanToWhseFormat(order.getDatePlanToWhseFormat());
        result.setChangeDatePlanWhse(order.isChangeDatePlanWhse());

        return  result;
    }

    /* -------------------------- Точность доставки заказов -------------------------------*/
    private PrecisionDeliveryOrderDTO mapOrderToPrecisionDeliveryOrder(OrderDTO order){
        PrecisionDeliveryOrderDTO result = new PrecisionDeliveryOrderDTO();
        result.setId(order.getId());
        result.setOrderNumber(order.getOrderNumber());
        result.setOrderLine(order.getOrderLine());
        result.setCustomer(order.getCustomer());
        result.setManager(order.getManager());
        result.setItemDescription(order.getItemDescription());
        result.setItem(order.getItem());
        result.setMonthShip(order.getMonthShip());
        result.setDeviation(order.getDeviation());
        result.setReasonDeviation(order.getReasonDeviation());
        result.setCalcStatus(order.getCalcStatus());
        result.setDateActualShip(order.getDateActualShip());
        result.setNornalizeGroupDate(order.getNornalizeGroupDate());
        result.setDeviationHide(order.getDeviationHide());
        result.setTypeCustomer(order.getTypeCustomer());
        result.setPlanDeliveryDateFormat(order.getPlanDeliveryDateFormat());
        result.setFactDeliveryDateFormat(order.getFactDeliveryDateFormat());
        result.setChangeDatePlanDelivery(order.isChangeDatePlanDelivery());

        return  result;
    }
}
