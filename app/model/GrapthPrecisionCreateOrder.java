package model;

import java.util.Date;

/**
 * Created by Gukov on 08.08.2016.
 */
public class GrapthPrecisionCreateOrder {

    private Integer series;
    private Date categoryAxis;

    public GrapthPrecisionCreateOrder(Integer series, Date categoryAxis) {
        this.series = series;
        this.categoryAxis = categoryAxis;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Date getCategoryAxis() {
        return categoryAxis;
    }

    public void setCategoryAxis(Date categoryAxis) {
        this.categoryAxis = categoryAxis;
    }
}
