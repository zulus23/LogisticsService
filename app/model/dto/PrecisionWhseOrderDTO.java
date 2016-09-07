package model.dto;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class PrecisionWhseOrderDTO extends BaseOrderDTO {
    /*Форматированная дата  даты фактического поступления на склад*/
    private String factOnWhseDateFormat;

    /*Форматированная дата  плановой даты поступления на склад*/
    private String datePlanToWhseFormat;

    private boolean isChangeDatePlanWhse;

    public String getFactOnWhseDateFormat() {
        return factOnWhseDateFormat;
    }

    public void setFactOnWhseDateFormat(String factOnWhseDateFormat) {
        this.factOnWhseDateFormat = factOnWhseDateFormat;
    }

    public String getDatePlanToWhseFormat() {
        return datePlanToWhseFormat;
    }

    public void setDatePlanToWhseFormat(String datePlanToWhseFormat) {
        this.datePlanToWhseFormat = datePlanToWhseFormat;
    }

    public boolean isChangeDatePlanWhse() {
        return isChangeDatePlanWhse;
    }

    public void setChangeDatePlanWhse(boolean changeDatePlanWhse) {
        isChangeDatePlanWhse = changeDatePlanWhse;
    }
}
