package services;

import model.Enterprise;
import model.TypeReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gukov on 05.08.2016.
 */
public class HelperForInterface {

     public List<TypeReport> typeReportList(){
         List<TypeReport> typeReports = new ArrayList<>();
         typeReports.add(new TypeReport(1,"Все заказы"));
         typeReports.add(new TypeReport(2,"Точность открытия заказов"));
         typeReports.add(new TypeReport(3,"Точность планирования заказов"));
         typeReports.add(new TypeReport(4,"Точность постановки заказов в производство"));
         typeReports.add(new TypeReport(5,"Точность выхода заказов на склад"));
         typeReports.add(new TypeReport(6,"Точность отгрузки заказов"));
         typeReports.add(new TypeReport(7,"Точность доставки заказов"));
         return  typeReports;

     }

    public List<Enterprise> listDB() {
        List<Enterprise> enterprises = new ArrayList<>();
            enterprises.add(new Enterprise(1,"ГОТЭК"));
            enterprises.add(new Enterprise(2,"ЦЕНТР"));
            enterprises.add(new Enterprise(3,"ГСЗ"));

        return enterprises;
    }
}
