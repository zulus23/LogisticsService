package model.dto;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class PrecisionProductionOrderDTO extends  BaseOrderDTO {
    /*Форматированная дата производства*/
    private String datePlanBeginProductionFormat;
    /*Форматированная дата  фактического  произ*/
    private String factProdReqDateFormat;


    public String getDatePlanBeginProductionFormat() {
        return datePlanBeginProductionFormat;
    }

    public void setDatePlanBeginProductionFormat(String datePlanBeginProductionFormat) {
        this.datePlanBeginProductionFormat = datePlanBeginProductionFormat;
    }

    public String getFactProdReqDateFormat() {
        return factProdReqDateFormat;
    }

    public void setFactProdReqDateFormat(String factProdReqDateFormat) {
        this.factProdReqDateFormat = factProdReqDateFormat;
    }
}
