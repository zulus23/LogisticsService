package model;

import com.avaje.ebean.Model;
import play.mvc.PathBindable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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
    private LocalDate monthActualShip;
    @Column(name = "DateCreate_Row")
    private LocalDate dataCreateOrder;
    @Column(name = "DatePlan_Mnfg")
    private LocalDate dataBeginProduction;
    /*@Column(name = "Item_Desc")
    private int deviation;
    @Column(name = "Item_Desc")
    private String deviationReason;
    private String statusOrder;*/


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

    public LocalDate getMonthActualShip() {
        return monthActualShip;
    }

    public void setMonthActualShip(LocalDate monthActualShip) {
        this.monthActualShip = monthActualShip;
    }

    public LocalDate getDataCreateOrder() {
        return dataCreateOrder;
    }

    public void setDataCreateOrder(LocalDate dataCreateOrder) {
        this.dataCreateOrder = dataCreateOrder;
    }

    public LocalDate getDataBeginProduction() {
        return dataBeginProduction;
    }

    public void setDataBeginProduction(LocalDate dataBeginProduction) {
        this.dataBeginProduction = dataBeginProduction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Finder<Long, ReportPrecisionCreateOrder> find = new Finder<Long,ReportPrecisionCreateOrder>(ReportPrecisionCreateOrder.class);
}
