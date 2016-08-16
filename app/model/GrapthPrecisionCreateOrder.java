package model;

import com.avaje.ebean.annotation.Sql;

import javax.persistence.Entity;
import java.sql.Date;

/**
 * Created by Gukov on 08.08.2016.
 */

@Entity
@Sql
public class GrapthPrecisionCreateOrder {
    private String deviation;
    private Date monthActualShip;
    private String customer;
    private Double procent;

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public Date getMonthActualShip() {
        return monthActualShip;
    }

    public void setMonthActualShip(Date monthActualShip) {
        this.monthActualShip = monthActualShip;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getProcent() {
        return procent;
    }

    public void setProcent(Double procent) {
        this.procent = procent;
    }
}
