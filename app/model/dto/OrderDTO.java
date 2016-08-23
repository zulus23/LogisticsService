package model.dto;

import model.Deviation;

import java.sql.Date;
import java.time.LocalDate;
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

    /*Форматированная дата  плановой  отгрузки*/
    private String datePlanShipFormat;


    /* Дата javascript*/
    private Date nornalizeGroupDate;


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
