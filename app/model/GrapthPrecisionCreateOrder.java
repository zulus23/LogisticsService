package model;

/**
 * Created by Gukov on 08.08.2016.
 */
public class GrapthPrecisionCreateOrder {

    private Integer series;
    private String  categoryAxis;

    public GrapthPrecisionCreateOrder(Integer series, String categoryAxis) {
        this.series = series;
        this.categoryAxis = categoryAxis;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getCategoryAxis() {
        return categoryAxis;
    }

    public void setCategoryAxis(String categoryAxis) {
        this.categoryAxis = categoryAxis;
    }
}
