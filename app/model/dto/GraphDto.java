package model.dto;

import java.sql.Date;

/**
 * Created by Gukov on 16.08.2016.
 */
public class GraphDto {

    private Date date;
    private Double procent;

    public Date getDate() {
        return date;
    }

    public Double getProcent() {
        return procent;
    }

    public GraphDto(Date date, Double procent) {
        this.date = date;
        this.procent = procent;
    }
}
