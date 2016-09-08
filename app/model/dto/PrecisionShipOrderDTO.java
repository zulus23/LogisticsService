package model.dto;

import java.sql.Date;

/**
 * Created by Zhukov on 07.09.2016.
 */
public class PrecisionShipOrderDTO extends BaseOrderDTO {

    /*Форматированная дата  фактической отгрузки*/
    private String dateActualShipFormat;
    /*Форматированная дата  плановой  отгрузки*/
    private String datePlanShipFormat;
    private boolean isChangeDatePlanShip;

    public String getDateActualShipFormat() {
        return dateActualShipFormat;
    }

    public void setDateActualShipFormat(String dateActualShipFormat) {
        this.dateActualShipFormat = dateActualShipFormat;
    }

    public String getDatePlanShipFormat() {
        return datePlanShipFormat;
    }

    public void setDatePlanShipFormat(String datePlanShipFormat) {
        this.datePlanShipFormat = datePlanShipFormat;
    }

    public boolean isChangeDatePlanShip() {
        return isChangeDatePlanShip;
    }

    public void setChangeDatePlanShip(boolean changeDatePlanShip) {
        isChangeDatePlanShip = changeDatePlanShip;
    }



}
