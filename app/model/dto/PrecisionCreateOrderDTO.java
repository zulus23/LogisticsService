package model.dto;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class PrecisionCreateOrderDTO extends BaseOrderDTO {

    /*Форматированная дата отгрузки*/
    private String dateCreateOrderFormat;

    /*Форматированная дата производства*/
    private String datePlanBeginProductionFormat;

    public String getDateCreateOrderFormat() {
        return dateCreateOrderFormat;
    }

    public void setDateCreateOrderFormat(String dateCreateOrderFormat) {
        this.dateCreateOrderFormat = dateCreateOrderFormat;
    }

    public String getDatePlanBeginProductionFormat() {
        return datePlanBeginProductionFormat;
    }

    public void setDatePlanBeginProductionFormat(String datePlanBeginProductionFormat) {
        this.datePlanBeginProductionFormat = datePlanBeginProductionFormat;
    }
}
