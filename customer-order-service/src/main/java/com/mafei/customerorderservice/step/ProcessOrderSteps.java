package com.mafei.customerorderservice.step;

import com.mafei.annotation.RequestFrom;
import com.mafei.bean.service.customerorder.ProcessOrder;
import com.mafei.flow.ExternalServiceCommandWorkflow;
import com.mafei.flow.SelfServiceCommandWorkflow;
import com.mafei.util.ServiceNames;
import com.mafei.util.StepManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/*
  @Author mafei
  @Created 7/20/2021 2:14 PM  
*/
@Component
public class ProcessOrderSteps extends StepManager {


    @Service
    @RequestFrom(name = ServiceNames.CUSTOMER_ORDER_SERVICE)
    public static class CheckCart implements SelfServiceCommandWorkflow<ProcessOrder.Aggregate> {


        @Autowired
        private KafkaTemplate<String, ProcessOrder.Aggregate> kafkaTemplate;

        @Override
        public String process(ProcessOrder.Aggregate aggregate, StepManager stepManager, Object... data) {
            stepManager.addSucceedServiceByRequestClass(this.getClass());
//            publishError(aggregate, stepManager);
            return "sucess";
        }

        @Override
        public Object revert(ProcessOrder.Aggregate aggregate, Object... data) throws Exception {
            return null;
        }

        @Override
        public void publishError(ProcessOrder.Aggregate aggregate, StepManager stepManager, Object... data) {
            System.out.println("CheckCart.publishError");
            System.out.println("kafkaTemplate = " + kafkaTemplate);
            aggregate.setServices(stepManager.getServices());
            ProducerRecord<String, ProcessOrder.Aggregate> message = new ProducerRecord<>(ProcessOrder.ERROR_TOPIC, "from " + this.getClass().getSimpleName(), aggregate);
            kafkaTemplate.send(message);
        }

        @Override
        @KafkaListener(topics = ProcessOrder.ERROR_TOPIC)
        public void consumeError(@Payload ProcessOrder.Aggregate aggregate, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey, ConsumerRecord<String, ProcessOrder.Aggregate> consumerRecord) {
            System.out.println("CheckCart.publishError");
            System.out.println("aggregate = " + aggregate);
            System.out.println("messageKey = " + messageKey);
        }
    }


    @Service
    @RequestFrom(name = ServiceNames.PAYMENT_SERVICE)
    public static class ProcessPayment implements ExternalServiceCommandWorkflow<ProcessOrder.Aggregate> {

        @Autowired
        private KafkaTemplate<String, ProcessOrder.Aggregate> kafkaTemplate;

        @Autowired
        private ProcessOrderStepsBase processOrderStepsBase;

        @Override
        public Boolean process(ProcessOrder.Aggregate aggregate, StepManager stepManager, Object... data) throws Exception {
            if (processOrderStepsBase.makePayment(aggregate)) {
                stepManager.addSucceedServiceByRequestClass(this.getClass());
                return true;
            } else {

                stepManager.addSucceedServiceByRequestClass(this.getClass());
                this.publishError(aggregate, stepManager);
                throw new Exception("error from " + this.getClass().getSimpleName());
            }
        }

        @Override
        public void publishError(ProcessOrder.Aggregate aggregate, StepManager stepManager, Object... data) {
            aggregate.setServices(stepManager.getServices());
            ProducerRecord<String, ProcessOrder.Aggregate> message = new ProducerRecord<>(ProcessOrder.ERROR_TOPIC, "from " + this.getClass().getSimpleName(), aggregate);
            kafkaTemplate.send(message);
        }
    }
}
