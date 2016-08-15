package model;

import com.avaje.ebean.Model;
import scala.Predef;

import javax.persistence.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by Gukov on 05.08.2016.
 */

@Entity
@Table(name = "GTK_RPT_LOGIST")
public class ReportPrecisionCreateOrder  extends Model{

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
    @Column(name = "DateActual_Ship")
    private Date monthActualShip;
    @Column(name = "DateCreate_Row")
    private LocalDate dateCreateOrder;
    @Column(name = "DatePlan_Mnfg")
    private LocalDate dateBeginProduction;
    @Column(name = "DatePlan_Ship")
    private LocalDate datePlanShip;

    /*@Column(name = "Item_Desc")
    private int deviation;
    @Column(name = "Item_Desc")
    private String deviationReason;*/
    @Column(name = "StatusRow")
    private String statusOrder;

    @Transient
    private String monthShip;
    @Transient
    private int yearShip;
    @Transient
    private int monthIntShip;
    @Transient
    private String dateCreateOrderFormat;
    @Transient
    private String dateBeginProductionFormat;

    @Transient
    private int deviation;
    @Transient
    private String caclStatus;


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

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePlanShip() {
        return datePlanShip;
    }

    public void setDatePlanShip(LocalDate datePlanShip) {
        this.datePlanShip = datePlanShip;
    }

    public String getMonthShip() {
        if(monthActualShip != null) {

            return monthActualShip.toLocalDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru-RU"));
        }
        return "";
    }
    public int getYearShip() {
        if(monthActualShip != null) {
            return monthActualShip.toLocalDate().getYear();
        }
        return 0;
    }

    public int getMonthIntShip() {
        if(monthActualShip != null) {
            return monthActualShip.toLocalDate().getMonthValue();
        }
        return 0;
    }

    public String getDateCreateOrderFormat() {

        if(dateCreateOrder != null) {
            return dateCreateOrder.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

         return "";
    }

    public String getDateBeginProductionFormat() {
        if(dateBeginProduction != null) {
            return dateBeginProduction.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
         return "";
    }

    public int getDeviation() {
        if(dateBeginProduction != null && dateCreateOrder != null) {

            int temp = Period.between(dateBeginProduction, dateCreateOrder).getDays();
            return temp < 3 && temp > 0 ? temp : 0;
        }
        return 0;
    }

    public String getCaclStatus() {
        Optional<String> status = Optional.ofNullable(statusOrder);
        //String temp =   Optional.ofNullable(statusOrder).orElseGet(()-> getDeviation() !=0 ? "С отклонением":"");
        return getDeviation() !=0 ? "С отклонением":"Без отклонений";
    }

    public static Finder<Long, ReportPrecisionCreateOrder> find = new Finder<Long,ReportPrecisionCreateOrder>(ReportPrecisionCreateOrder.class);
}
