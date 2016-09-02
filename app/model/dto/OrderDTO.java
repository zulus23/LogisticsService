package model.dto;

import model.Deviation;

import javax.persistence.Column;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Created by Gukov on 22.08.2016.
 */
public class OrderDTO {
    private Long id;

    private String orderNumber;

    private String orderLine;

    private String customer;

    private String manager;

    private String item;

    private String itemDescription;

    /* Дата фактической отгрузки*/
    private Date dateActualShip;
    /* Дата создания заказа*/
    private LocalDate dateCreateOrder;
    /* Дата начала производства */
    private LocalDate datePlanBeginProduction;
    /* Дата плановой  отгрузки*/
    private LocalDate datePlanShip;

    /* Дата планового  поступления на склад*/
    private LocalDate datePlanWhse;

   /* Дата плановой  доставки*/
   private LocalDate datePlanDelivery;

    /*Фактическая дата начала производства в плане на сутки*/
    private Date factProdReqDate;
    /*Фактическая дата поступления на склад*/
    private Date factOnWhseDate;
    /*Фактическая дата доставки*/
    private Date factDeliveryDate;
    /*Астуализированная  дата доставки*/
    private Date actualDeliveryDate;
    /*Плановая дата доставки*/
    private Date planDeliveryDate;
    /*Плановая дата производства*/
    private Date planProdReqDate;

    /*Плановая дата доставки из заявки на отгрузку*/
    private Date planDeliveryDate_M;
    /* Дата плановой  отгрузки из заявки на отгрузку*/
    private Date datePlanShip_M;
    /*Тип транспорта */

    private String typeShip;



    /* ==================== Для режима расчета дат (режим 2) ====================*/
    /* Дата планового  поступления на склад не измененная*/
    private LocalDate datePlanWhseOriginal;
    /* Дата плановой  отгрузки не измененная*/
    private LocalDate datePlanShipOriginal;

    /* Дата плановой  доставки не измененная*/
    private LocalDate datePlanDeliveryOriginal;

    /* ==========================================================================*/

    /* Причина отклонения*/
    private String reasonDeviation;
    /* величина отклонения*/
    private int deviation;

    /*Служить для расчета процента*/
    private int deviationHide;

    /* Статус заказа с отклонением или без*/
    private String calcStatus;

    /*Месяц отгрузки*/
    private String monthShip;
    /*Форматированная дата отгрузки*/
    private String dateCreateOrderFormat;
    /*Форматированная дата производства*/
    private String datePlanBeginProductionFormat;

    /*Форматированная дата  плановой даты поступления на склад*/
    private String datePlanToWhseFormat;
    /*Форматированная дата  фактической отгрузки*/
    private String dateActualShipFormat;

    /*Форматированная дата  даты фактического поступления на склад*/
    private String factOnWhseDateFormat;

    /*Форматированная дата  плановой  отгрузки*/
    private String datePlanShipFormat;

    /*Форматированная дата  фактического  произ*/
    private String factProdReqDateFormat;
    /*Форматированная дата  фактической  доставки*/
    private String factDeliveryDateFormat;
    /*Форматированная дата  плановой  доставки*/
    private String actualDeliveryDateFormat;


    /*Форматированная дата  актуальной   доставки*/
    private String planDeliveryDateFormat;
    /*Форматированная дата  плана   доставки*/
    private String datePlanDeliveryFormat;


    /* Дата javascript*/
    private Date nornalizeGroupDate;

    private boolean isChangeDatePlanShip;
    private boolean isChangeDatePlanWhse;
    private boolean isChangeDatePlanDelivery;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(String orderLine) {
        this.orderLine = orderLine;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Date getDateActualShip() {
        return dateActualShip;
    }

    public void setDateActualShip(Date dateActualShip) {
        this.dateActualShip = dateActualShip;
    }

    public LocalDate getDateCreateOrder() {
        return dateCreateOrder;
    }

    public void setDateCreateOrder(LocalDate dateCreateOrder) {
        this.dateCreateOrder = dateCreateOrder;
    }

    public LocalDate getDatePlanBeginProduction() {
        return datePlanBeginProduction;
    }

    public void setDatePlanBeginProduction(LocalDate datePlanBeginProduction) {
        this.datePlanBeginProduction = datePlanBeginProduction;
    }

    public LocalDate getDatePlanShip() {
        return datePlanShip;
    }

    public void setDatePlanShip(LocalDate datePlanShip) {
        this.datePlanShip = datePlanShip;
    }

    public LocalDate getDatePlanWhse() {
        return datePlanWhse;
    }

    public void setDatePlanWhse(LocalDate datePlanWhse) {
        this.datePlanWhse = datePlanWhse;
    }

    public String getReasonDeviation() {
        return reasonDeviation;
    }

    public void setReasonDeviation(String reasonDeviation) {
        this.reasonDeviation = reasonDeviation;
    }

    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

    public String getCalcStatus() {
        return calcStatus;
    }

    public void setCalcStatus(String calcStatus) {
        this.calcStatus = calcStatus;
    }

    public int getDeviationHide() {
        return calcStatus == Deviation.NO.getName() ? 0:1 ;
    }


    public LocalDate getDatePlanWhseOriginal() {
        return datePlanWhseOriginal;
    }

    public void setDatePlanWhseOriginal(LocalDate datePlanWhseOriginal) {
        this.datePlanWhseOriginal = datePlanWhseOriginal;
    }

    public LocalDate getDatePlanShipOriginal() {
        return datePlanShipOriginal;
    }

    public void setDatePlanShipOriginal(LocalDate datePlanShipOriginal) {
        this.datePlanShipOriginal = datePlanShipOriginal;
    }

    public LocalDate getDatePlanDeliveryOriginal() {
        return datePlanDeliveryOriginal;
    }

    public void setDatePlanDeliveryOriginal(LocalDate datePlanDeliveryOriginal) {
        this.datePlanDeliveryOriginal = datePlanDeliveryOriginal;
    }

    public Date getFactProdReqDate() {
        return factProdReqDate;
    }

    public void setFactProdReqDate(Date factProdReqDate) {
        this.factProdReqDate = factProdReqDate;
    }

    public Date getFactOnWhseDate() {
        return factOnWhseDate;
    }

    public void setFactOnWhseDate(Date factOnWhseDate) {
        this.factOnWhseDate = factOnWhseDate;
    }

    public Date getFactDeliveryDate() {
        return factDeliveryDate;
    }

    public void setFactDeliveryDate(Date factDeliveryDate) {
        this.factDeliveryDate = factDeliveryDate;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public Date getPlanDeliveryDate() {
        return planDeliveryDate;
    }

    public void setPlanDeliveryDate(Date planDeliveryDate) {
        this.planDeliveryDate = planDeliveryDate;
    }

    public Date getPlanProdReqDate() {
        return planProdReqDate;
    }

    public void setPlanProdReqDate(Date planProdReqDate) {
        this.planProdReqDate = planProdReqDate;
    }

    public Date getPlanDeliveryDate_M() {
        return planDeliveryDate_M;
    }

    public void setPlanDeliveryDate_M(Date planDeliveryDate_M) {
        this.planDeliveryDate_M = planDeliveryDate_M;
    }

    public Date getDatePlanShip_M() {
        return datePlanShip_M;
    }

    public void setDatePlanShip_M(Date datePlanShip_M) {
        this.datePlanShip_M = datePlanShip_M;
    }

    public String getTypeShip() {
        return typeShip;
    }

    public void setTypeShip(String typeShip) {
        this.typeShip = typeShip;
    }

    public String getMonthShip() {
        if(dateActualShip != null) {

            return String.format("%s-%s",  dateActualShip.toLocalDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru-RU")),
                    dateActualShip.toLocalDate().getYear());
        }
        return "";
    }


    public String getDateCreateOrderFormat() {
        if(getDateCreateOrder() != null) {
            return getDateCreateOrder().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

        return "";
    }



    public String getDatePlanBeginProductionFormat() {
        if(getDatePlanBeginProduction() != null) {
            return getDatePlanBeginProduction().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";
    }



    public String getDatePlanToWhseFormat() {
        if(getDatePlanWhse() != null) {
            return getDatePlanWhse().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";
    }

    public void setDatePlanToWhseFormat(String datePlanToWhseFormat) {
        this.datePlanToWhseFormat = datePlanToWhseFormat;
    }

    public String getDateActualShipFormat() {

        if(dateActualShip != null) {
            return dateActualShip.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

        return "";

    }

    public String getDatePlanShipFormat(){
        if(datePlanShip != null) {
            return datePlanShip.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

        return "";
    }
    public String getFactOnWhseDateFormat(){
        if(factOnWhseDate != null) {
            return factOnWhseDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

        return "";
    }

    public String getFactProdReqDateFormat() {
        if(factProdReqDate != null) {
            return factProdReqDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";
    }

    public String getFactDeliveryDateFormat() {
        if(factDeliveryDate != null) {
            return factDeliveryDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";

    }

    public String getPlanDeliveryDateFormat() {
        if(planDeliveryDate != null) {
            return planDeliveryDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";

    }

    public String getActualDeliveryDateFormat() {
        if(actualDeliveryDate != null) {
            return actualDeliveryDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";

    }

    public String getDatePlanDeliveryFormat() {
        if(datePlanDelivery != null) {
            return datePlanDelivery.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";

    }

    public Date getNornalizeGroupDate() {
        LocalDate temp;
        if(dateActualShip != null) {
            temp = dateActualShip.toLocalDate();

        } else {
            temp = LocalDate.now();
        }
        return  Date.valueOf(LocalDate.of(temp.getYear(),temp.getMonth(),1));
    }

    public void setNornalizeGroupDate(Date nornalizeGroupDate) {
        this.nornalizeGroupDate = nornalizeGroupDate;
    }

    public boolean isChangeDatePlanShip() {
        if(datePlanShipOriginal != null && datePlanShip !=null) {
            return Period.between(datePlanShipOriginal, datePlanShip).getDays() != 0;
        }
        return false;

    }

    public boolean isChangeDatePlanWhse() {
        if(datePlanWhseOriginal != null && datePlanWhse !=null) {
            return Period.between(datePlanWhseOriginal, datePlanWhse).getDays() != 0;
        }
        return false;
    }

    public boolean isChangeDatePlanDelivery() {
        if(datePlanDeliveryOriginal != null && datePlanDelivery !=null) {
            return Period.between(datePlanDeliveryOriginal, datePlanDelivery).getDays() != 0;
        }
        return  false;
    }

    public LocalDate getDatePlanDelivery() {
        return datePlanDelivery;
    }

    public void setDatePlanDelivery(LocalDate datePlanDelivery) {
        this.datePlanDelivery = datePlanDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        return getId().equals(orderDTO.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
