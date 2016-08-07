package services;

import model.Enterprise;
import model.TypeReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gukov on 05.08.2016.
 */
public class HelperForInterface {
    List<TypeReport> typeReports = new ArrayList<>();
    List<Enterprise> enterprises = new ArrayList<>();


     public List<TypeReport> typeReportList(){
         typeReports.clear();
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
           enterprises.clear();
            enterprises.add(new Enterprise(1,"ГОТЭК","GOTEK"));
            enterprises.add(new Enterprise(2,"ЦЕНТР","Center"));
            enterprises.add(new Enterprise(3,"ГСЗ","SPB"));

        return enterprises;
    }

    public String siteName(int id){
        listDB();
        return enterprises.stream().filter(s -> s.getId() == id).map(e -> e.getNameInDb()).findFirst().get();
    }
}
