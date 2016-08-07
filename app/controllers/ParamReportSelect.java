package controllers;

import java.time.LocalDate;

/**
 * Created by Zhukov on 05.08.2016.
 */
public class ParamReportSelect {

    private String dateBegin;
    private String dateEnd;
    private String enterprise;
    private String typeReport;
    private String mode;

    public ParamReportSelect() {
        this(LocalDate.now().toString(),LocalDate.now().toString(),"1","1","1");
    }

    public ParamReportSelect(String dateBegin, String dateEnd, String enterprise, String typeReport, String mode) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.enterprise = enterprise;
        this.typeReport = typeReport;
        this.mode = mode;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    @Override
    public String toString() {
        return "ParamReportSelect{" +
                "dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                ", enterprise='" + enterprise + '\'' +
                ", typeReport='" + typeReport + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
