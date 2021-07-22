package com.mafei.flow;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/*
  @Author mafei
  @Created 7/20/2021 2:06 PM  
*/
public interface SubSelfServiceCommandWorkflow<T> {

    void consumeError(T aggregate, String messageKey, ConsumerRecord<String, T> consumerRecord);
}
