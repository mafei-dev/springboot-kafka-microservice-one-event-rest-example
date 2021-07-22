package com.mafei.paymentservice.controller;

import com.mafei.bean.service.customerorder.ProcessOrder;
import com.mafei.flow.SubSelfServiceCommandWorkflow;
import com.mafei.util.ServiceNames;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/*
  @Author mafei
  @Created 7/22/2021 4:54 PM  
*/
@RestController
@RequestMapping("/payment/v1/make-payment")
@Log4j2
public class MakePaymentController implements SubSelfServiceCommandWorkflow<ProcessOrder.Aggregate> {

    @PostMapping
    public Boolean _do(@RequestBody ProcessOrder.Aggregate aggregate) {
        log.info("aggregate {}", aggregate);
        if (aggregate.getRequest().getShop_customer_id() > 5) {
            return true;
        } else {
            return false;
        }
    }

    @KafkaListener(topics = ProcessOrder.ERROR_TOPIC)
    @Override
    public void consumeError(@Payload ProcessOrder.Aggregate aggregate, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey, ConsumerRecord<String, ProcessOrder.Aggregate> consumerRecord) {
        if (aggregate.getServices().contains(ServiceNames.PAYMENT_SERVICE)) {
            System.out.println("MakePaymentController.consumeError");
            System.out.println("aggregate = " + aggregate);
        }
    }
}
