package model;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Date;
import java.time.LocalDate;
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
    private LocalDate datePlanBeginProduction;
    /*Дата плановой отгрузки*/
    @Column(name = "DatePlan_Ship")
    private LocalDate datePlanShip;

    /*Плановая дата поступления на склад*/
    @Column(name = "DatePlan_Whse")
    private LocalDate datePlanWhse;
    /*Фактическая дата начала производства в плане на сутки*/
    @Column(name = "FactProdReqDate")
    private Date factProdReqDate;

    /*Фактическая дата поступления на склад*/
    @Column(name = "FactOnWhseDate")
    private Date factOnWhseDate;

    /*Фактическая дата доставки*/
    @Column(name = "FactDeliveryDate")
    private Date factDeliveryDate;

    /*Астуализированная  дата доставки*/
    @Column(name = "ActualDeliveryDate")
    private Date actualDeliveryDate;

    /*Плановая дата доставки*/
    @Column(name = "PlanDeliveryDate")
    private Date planDeliveryDate;

    /*Плановая дата производства*/
    @Column(name = "PlanProdReqDate")
    private Date planProdReqDate;


    @Column(name = "StatusRow")
    private String reasonDeviation;

    /*Плановая дата доставки из заявки диспечера*/
    @Column(name = "PlanDeliveryDate_m")
    private Date planDeliveryDate_M;

    /*Дата плановой отгрузки из заявки диспечера*/
    @Column(name = "DatePlan_Ship_M")
    private Date datePlanShip_M;

    /*Тип транспорта */
    @Column(name = "ShipCodeDescr")
    private String typeShip;

    /*Тип клиента */
    @Column(name = "CustStrateg")
    private String typeCustomer;



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

    public LocalDate getDatePlanBeginProduction() {
        return datePlanBeginProduction;
    }

    public void setDatePlanBeginProduction(LocalDate datePlanBeginProduction) {
        this.datePlanBeginProduction = datePlanBeginProduction;
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

    public LocalDate getDatePlanWhse() {
        return datePlanWhse;
    }

    public void setDatePlanWhse(LocalDate datePlanWhse) {
        this.datePlanWhse = datePlanWhse;
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

    public String getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(String typeCustomer) {
        this.typeCustomer = typeCustomer;
    }
}
