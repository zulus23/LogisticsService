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

    public static Finder<Long, ReportPrecisionCreateOrder> find = new Finder<Long,ReportPrecisionCreateOrder>(ReportPrecisionCreateOrder.class);
}
