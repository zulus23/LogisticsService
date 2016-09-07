package model.dto;

import java.sql.Date;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class BaseOrderDTO {
    private Long id;
    private String orderNumber;
    private String orderLine;
    private String customer;
    private String manager;
    private String item;
    private String itemDescription;
    /* Дата фактической отгрузки*/
    private Date dateActualShip;
    /* Причина отклонения*/
    private String reasonDeviation;
    /* величина отклонения*/
    private int deviation;
    /*Служить для расчета процента*/
    private int deviationHide;
    /* Дата javascript*/
    private Date nornalizeGroupDate;
    /* Статус заказа с отклонением или без*/
    private String calcStatus;
    /*Месяц отгрузки*/
    private String monthShip;
    private String typeCustomer;

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

    public int getDeviationHide() {
        return deviationHide;
    }

    public void setDeviationHide(int deviationHide) {
        this.deviationHide = deviationHide;
    }

    public Date getNornalizeGroupDate() {
        return nornalizeGroupDate;
    }

    public void setNornalizeGroupDate(Date nornalizeGroupDate) {
        this.nornalizeGroupDate = nornalizeGroupDate;
    }

    public String getCalcStatus() {
        return calcStatus;
    }

    public void setCalcStatus(String calcStatus) {
        this.calcStatus = calcStatus;
    }

    public String getMonthShip() {
        return monthShip;
    }

    public void setMonthShip(String monthShip) {
        this.monthShip = monthShip;
    }

    public String getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(String typeCustomer) {
        this.typeCustomer = typeCustomer;
    }
}
