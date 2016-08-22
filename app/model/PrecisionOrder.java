package model;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Zhukov on 22.08.2016.
 */

@MappedSuperclass
public abstract class PrecisionOrder extends Model {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "site")
    private String site;
    @Column(name = "co_num")
    private String orderNumber;
    @Column(name = "co_line")
    private String orderLine;
    @Column(name = "cust_name")
    private String customer;
    @Column(name = "FrontSlsmanName")
    private String manager;
    @Column(name = "item")
    private String item;
    @Column(name = "Item_Desc")
    private String itemDescription;

    /*Дата фактической отгрузки*/
    @Column(name = "DateActual_Ship")
    private Date monthActualShip;
    @Column(name = "DateCreate_Row")
    private LocalDate dateCreateOrder;
    /*Дата планового производства*/
    @Column(name = "DatePlan_Mnfg")
    private LocalDate dateBeginProduction;
    /*Дата плановой отгрузки*/
    @Column(name = "DatePlan_Ship")
    private LocalDate datePlanShip;

    /*Плановая дата поступления на склад*/
    @Column(name = "DatePlan_Whse")
    private LocalDate datePlanWhse;

    @Column(name = "StatusRow")
    private String reasonDeviation;

/*
    @Transient
    private int deviation;
    @Transient
    private String calcStatus;
    @Transient
    private String monthShip;*/

/*

    @Transient
    private String dateCreateOrderFormat;
    @Transient
    private String dateBeginProductionFormat;
    @Transient
    private Date nornalizeGroupDate;
*/




    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getMonthActualShip() {
        return monthActualShip;
    }

    public void setMonthActualShip(Date monthActualShip) {
        this.monthActualShip = monthActualShip;
    }

    public LocalDate getDateCreateOrder() {
        return dateCreateOrder;
    }

    public void setDateCreateOrder(LocalDate dateCreateOrder) {
        this.dateCreateOrder = dateCreateOrder;
    }

    public LocalDate getDateBeginProduction() {
        return dateBeginProduction;
    }

    public void setDateBeginProduction(LocalDate dateBeginProduction) {
        this.dateBeginProduction = dateBeginProduction;
    }
    public String getReasonDeviation() {
        if( Objects.isNull(reasonDeviation)) return "";
        return  reasonDeviation;
    }

    public void setReasonDeviation(String reasonDeviation) {
        this.reasonDeviation = reasonDeviation;
    }

    public LocalDate getDatePlanShip() {
        return datePlanShip;
    }

    public void setDatePlanShip(LocalDate datePlanShip) {
        this.datePlanShip = datePlanShip;
    }

    /*public String getMonthShip() {
        if(getMonthActualShip() != null) {

            return String.format("%s-%s",  getMonthActualShip().toLocalDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru-RU")),
                    getMonthActualShip().toLocalDate().getYear());
        }
        return "";
    }*/

    public LocalDate getDatePlanWhse() {
        return datePlanWhse;
    }

    public void setDatePlanWhse(LocalDate datePlanWhse) {
        this.datePlanWhse = datePlanWhse;
    }

  /*  public String getDateBeginProductionFormat() {
        if(getDateBeginProduction() != null) {
            return getDateBeginProduction().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";
    }*/

   /* public String getDatePlanWhseFormat() {
        if(getDatePlanWhse() != null) {
            return getDatePlanWhse().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
        return "";
    }*/

    /*public String getDateCreateOrderFormat() {

        if(getDateCreateOrder() != null) {
            return getDateCreateOrder().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

        return "";
    }
*/





  /*  public Date getNornalizeGroupDate() {
        LocalDate temp;
        if(getMonthActualShip() != null) {
            temp = getMonthActualShip().toLocalDate();

        } else {
            temp = LocalDate.now();
        }
        return  Date.valueOf(LocalDate.of(temp.getYear(),temp.getMonth(),1));
    }*/

    //public abstract int getDeviation();
    //public abstract String getCalcStatus();
}
