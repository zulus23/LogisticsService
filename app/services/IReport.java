package services;

import model.ReportPrecisionCreateOrder;
import model.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by Zhukov on 03.09.2016.
 */
public interface IReport {

    List<OrderDTO> getData();


    public default   OrderDTO createOrderDTO(ReportPrecisionCreateOrder order){

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setOrderLine(order.getOrderLine());
        orderDTO.setCustomer(order.getCustomer());
        orderDTO.setManager(order.getManager());
        orderDTO.setItem(order.getItem());
        orderDTO.setItemDescription(order.getItemDescription());
        orderDTO.setDateActualShip(order.getMonthActualShip());
        orderDTO.setDateCreateOrder(order.getDateCreateOrder());
        orderDTO.setDatePlanBeginProduction(order.getDatePlanBeginProduction());
        orderDTO.setDatePlanShip(order.getDatePlanShip());
        orderDTO.setDatePlanWhse(order.getDatePlanWhse());
        orderDTO.setDatePlanShipOriginal(order.getDatePlanShip());
        orderDTO.setDatePlanWhseOriginal(order.getDatePlanWhse());
        /*TODO необходмисо сохранить оригинальную дату доставки*/
        if(Optional.of(order.getPlanDeliveryDate()).isPresent()) {
            orderDTO.setDatePlanDeliveryOriginal(order.getPlanDeliveryDate().toLocalDate());
        }
        if(Optional.of(order.getDatePlanWhse()).isPresent()) {
            orderDTO.setDatePlanWhseOriginal(order.getDatePlanWhse());
        }
        if(Optional.of(order.getDatePlanShip()).isPresent()) {
            orderDTO.setDatePlanShipOriginal(order.getDatePlanShip());
        }

        orderDTO.setActualDeliveryDate(order.getActualDeliveryDate());
        orderDTO.setFactDeliveryDate(order.getFactDeliveryDate());
        orderDTO.setFactOnWhseDate(order.getFactOnWhseDate());
        orderDTO.setFactProdReqDate(order.getFactProdReqDate());
        orderDTO.setPlanDeliveryDate(order.getPlanDeliveryDate());
        orderDTO.setPlanProdReqDate(order.getPlanProdReqDate());

        orderDTO.setReasonDeviation(order.getReasonDeviation());
        orderDTO.setPlanDeliveryDate_M(order.getPlanDeliveryDate_M());
        orderDTO.setDatePlanShip_M(order.getDatePlanShip_M());
        orderDTO.setTypeShip(order.getTypeShip());


        return  orderDTO;
    }



}
