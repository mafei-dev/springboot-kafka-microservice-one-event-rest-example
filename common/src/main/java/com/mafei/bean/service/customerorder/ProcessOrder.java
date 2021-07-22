package com.mafei.bean.service.customerorder;

import com.mafei.util.CompletedRequests;
import lombok.*;

/*
  @Author mafei
  @Created 7/20/2021 1:58 PM  
*/
public class ProcessOrder {

    public static final String ERROR_TOPIC = "PROCESS_ORDER_FAILED";

    @Data
    @NoArgsConstructor
    public static class Request {
        private Integer user_id;
        private Integer customer_id;
        private Boolean is_shop_customer;
        private Integer shop_customer_id;
        private Integer shop_id;
        private Integer temporary_cart_id;
    }

    @Data
    @Builder
    public static class Response {

    }


    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Aggregate extends CompletedRequests {
        private Request request;

        public Aggregate(Request request) {
            this.request = request;
        }
    }
}
