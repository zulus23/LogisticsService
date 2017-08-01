package services;

import controllers.PrecisionOrderController;
import scala.Option;

import javax.inject.Inject;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

/**
 * Created by Zhukov on 03.09.2016.
 */
public class SelectParam {


    private Option<String> dateBegin;
    private Option<String> dateEnd;
    private Option<String> site;
    private Option<String> mode;
    private LocalDate tempdateBegin;
    private LocalDate tempdateEnd;
    private String tempSite;
    private String tempMode;

    public SelectParam(Option<String> dateBegin, Option<String> dateEnd, Option<String> site, Option<String> mode) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.site = site;
        this.mode = mode;
    }

    public LocalDate getTempdateBegin() {
        return tempdateBegin;
    }

    public LocalDate getTempdateEnd() {
        return tempdateEnd;
    }

    public String getTempSite() {
        return tempSite;
    }

    public String getTempMode() {
        return tempMode;
    }

    public SelectParam invoke() {
        tempdateBegin = LocalDate.now();
        tempdateEnd = LocalDate.now();

        if (dateBegin.isDefined()) {
            try {
                tempdateBegin = LocalDate.parse(dateBegin.get(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e ){

                tempdateBegin = LocalDate.parse(dateBegin.get(), DateTimeFormatter.ISO_LOCAL_DATE);

            }
        }
        if (dateEnd.isDefined()) {
            try {
                tempdateEnd = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e ){
                tempdateEnd = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        }

        tempSite = "";

        if (site.isDefined()) {
            tempSite = HelperForInterface.siteName(Integer.parseInt(site.get()));
        }
        tempMode = "1";
        if (mode.isDefined()) {
            tempMode = mode.get();
        }
        return this;
    }
}
