package com.mafei.customerorderservice.controller;

import com.mafei.annotation.RequestFrom;
import com.mafei.bean.service.customerorder.ProcessOrder;
import com.mafei.customerorderservice.step.ProcessOrderSteps;
import com.mafei.customerorderservice.step.ProcessOrderStepsBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/*
  @Author mafei
  @Created 7/20/2021 1:55 PM  
*/
@RestController
@RequestMapping("/customer-order/v1/order")
public class CustomerOrderController {


    @Autowired
    private ProcessOrderSteps processOrderSteps;

    @Autowired
    private ProcessOrderSteps.CheckCart checkCart;

    @Autowired
    private ProcessOrderSteps.ProcessPayment processPayment;


    @GetMapping
    public ResponseEntity<?> processOrder() {
        ProcessOrder.Request request = new ProcessOrder.Request();
        request.setIs_shop_customer(false);
        request.setCustomer_id(1);
        request.setUser_id(2);
        request.setShop_customer_id(2);
        request.setTemporary_cart_id(3);
        ProcessOrder.Aggregate aggregate = new ProcessOrder.Aggregate(request);

        {//checkCart
            checkCart.process(aggregate, processOrderSteps);
            System.out.println("1.0-processOrderSteps = " + processOrderSteps.getServices());
        }

        {//make payment
            try {
                System.out.println("aggregate = " + aggregate);
                processPayment.process(aggregate, processOrderSteps);
                System.out.println("2.0-processOrderSteps = " + processOrderSteps.getServices());
                return ResponseEntity.ok().body("success.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }


    }

}
