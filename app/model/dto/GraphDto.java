package model.dto;

import java.sql.Date;

/**
 * Created by Gukov on 16.08.2016.
 */
public class GraphDto {

    private Date date;
    private Double procent;
    private String customer;
    public Date getDate() {
        return date;
    }

    public Double getProcent() {
        return procent;
    }

    public String getCustomer() {
        return customer;
    }

    public GraphDto(Date date, Double procent, String customer) {
        this.date = date;
        this.procent = procent;
        this.customer = customer;
    }



    public GraphDto(Date date, Double procent) {
        this(date,procent,"");

    }
}
