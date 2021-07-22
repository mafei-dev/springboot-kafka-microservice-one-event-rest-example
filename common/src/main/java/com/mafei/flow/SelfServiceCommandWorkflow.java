package com.mafei.flow;

import com.mafei.util.StepManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/*
  @Author mafei
  @Created 7/20/2021 2:06 PM  
*/
public interface SelfServiceCommandWorkflow<T> {

    Object process(T aggregate, StepManager stepManager, Object... data) throws Exception;

    Object revert(T aggregate, Object... data) throws Exception;

    void publishError(T aggregate, StepManager stepManager, Object... data);

    void consumeError(T aggregate, String messageKey, ConsumerRecord<String, T> consumerRecord);
}
