package model;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Gukov on 05.08.2016.
 */

@Entity
@Table(name = "GTK_RPT_LOGIST")
public class ReportPrecisionCreateOrder extends PrecisionOrder {

   /* @Column(name = "DateActual_Ship")
    private Date monthActualShip;
    @Column(name = "DateCreate_Row")
    private LocalDate dateCreateOrder;
    @Column(name = "DatePlan_Mnfg")
    private LocalDate dateBeginProduction;
    @Column(name = "DatePlan_Ship")
    private LocalDate datePlanShip;

    @Column(name = "StatusRow")
    private String reasonDeviation;*/

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
    private String calcStatus;

    @Transient
    private Date nornalizeGroupDate;

/*
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
    }*/

    public String getMonthShip() {
        if(getMonthActualShip() != null) {

            return String.format("%s-%s",  getMonthActualShip().toLocalDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru-RU")),
                                          getMonthActualShip().toLocalDate().getYear());
        }
        return "";
    }
   /* public int getYearShip() {
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
    }*/

    public String getDateCreateOrderFormat() {

        if(getDateCreateOrder() != null) {
            return getDateCreateOrder().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }

         return "";
    }

    public String getDateBeginProductionFormat() {
        if(getDateBeginProduction() != null) {
            return getDateBeginProduction().format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ru-RU")));
        }
         return "";
    }

    public int getDeviation() {
        if(getDateBeginProduction() != null && getDateCreateOrder() != null) {

            int temp = Period.between(getDateCreateOrder(),getDateBeginProduction() ).getDays();
            return /*temp < 3 && */temp > 2 ? 0:temp ;
        }
        return 0;
    }

    public Date getNornalizeGroupDate() {
        LocalDate temp;
        if(getMonthActualShip() != null) {
           temp = getMonthActualShip().toLocalDate();

        } else {
            temp = LocalDate.now();
        }
        return  Date.valueOf(LocalDate.of(temp.getYear(),temp.getMonth(),1));
    }

    public String getCalcStatus() {

        return getDeviation() !=0 ? Deviation.YES.getName():Deviation.NO.getName();
    }

    public static Finder<Long, ReportPrecisionCreateOrder> find = new Finder<Long,ReportPrecisionCreateOrder>(ReportPrecisionCreateOrder.class);
}
