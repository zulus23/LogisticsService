package model;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Gukov on 05.08.2016.
 */

@Entity
@Table(name = "GTK_RPT_LOGIST")
public class ReportPrecisionCreateOrder extends PrecisionOrder {


/*
    public int getDeviation() {
        if(getDateBeginProduction() != null && getDateCreateOrder() != null) {

            int temp = Period.between(getDateCreateOrder(),getDateBeginProduction() ).getDays();
            return *//*temp < 3 && *//*temp > 2 ? 0:temp ;
        }
        return 0;
    }

    public String getCalcStatus() {

        return getDeviation() !=0 ? Deviation.YES.getName():Deviation.NO.getName();
    }*/

    public static Finder<Long, ReportPrecisionCreateOrder> find = new Finder<Long,ReportPrecisionCreateOrder>(ReportPrecisionCreateOrder.class);
}
