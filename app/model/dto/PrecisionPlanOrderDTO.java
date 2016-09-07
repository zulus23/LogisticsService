package model.dto;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class PrecisionPlanOrderDTO extends BaseOrderDTO {
    /*Форматированная дата  актуальной   доставки*/
    private String planDeliveryDateFormat;
    /*Форматированная дата  плановой  доставки*/
    private String actualDeliveryDateFormat;

    public String getPlanDeliveryDateFormat() {
        return planDeliveryDateFormat;
    }

    public void setPlanDeliveryDateFormat(String planDeliveryDateFormat) {
        this.planDeliveryDateFormat = planDeliveryDateFormat;
    }

    public String getActualDeliveryDateFormat() {
        return actualDeliveryDateFormat;
    }

    public void setActualDeliveryDateFormat(String actualDeliveryDateFormat) {
        this.actualDeliveryDateFormat = actualDeliveryDateFormat;
    }
}
