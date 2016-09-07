package model.dto;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class PrecisionDeliveryOrderDTO extends BaseOrderDTO {
    /*Форматированная дата  актуальной   доставки*/
    private String planDeliveryDateFormat;
    /*Форматированная дата  фактической  доставки*/
    private String factDeliveryDateFormat;

    private boolean isChangeDatePlanDelivery;


    public String getPlanDeliveryDateFormat() {
        return planDeliveryDateFormat;
    }

    public void setPlanDeliveryDateFormat(String planDeliveryDateFormat) {
        this.planDeliveryDateFormat = planDeliveryDateFormat;
    }

    public String getFactDeliveryDateFormat() {
        return factDeliveryDateFormat;
    }

    public void setFactDeliveryDateFormat(String factDeliveryDateFormat) {
        this.factDeliveryDateFormat = factDeliveryDateFormat;
    }

    public boolean isChangeDatePlanDelivery() {
        return isChangeDatePlanDelivery;
    }

    public void setChangeDatePlanDelivery(boolean changeDatePlanDelivery) {
        isChangeDatePlanDelivery = changeDatePlanDelivery;
    }
}
