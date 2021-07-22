package com.mafei.customerorderservice.step;

import com.mafei.bean.service.customerorder.ProcessOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
  @Author mafei
  @Created 7/22/2021 3:52 PM  
*/
@FeignClient(name = "payment", url = "localhost:8080/payment", fallbackFactory = ProcessOrderStepsBase.FallBack.class)
public interface ProcessOrderStepsBase {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/make-payment", consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean makePayment(ProcessOrder.Aggregate aggregate);

    @Log4j2
    class FallBack implements FallbackFactory<ProcessOrderStepsBase> {

        @Override
        public ProcessOrderStepsBase create(Throwable cause) {
            cause.printStackTrace();
            log.error("error " + cause.getMessage());
            return null;
        }
    }
}
