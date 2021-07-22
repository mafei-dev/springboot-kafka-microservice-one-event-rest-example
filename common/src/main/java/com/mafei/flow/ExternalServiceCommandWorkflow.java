package com.mafei.flow;

import com.mafei.util.StepManager;

/*
  @Author mafei
  @Created 7/20/2021 2:06 PM  
*/
public interface ExternalServiceCommandWorkflow<T> {

    Object process(T aggregate, StepManager stepManager, Object... data) throws Exception;

    void publishError(T aggregate, StepManager stepManager, Object... data);
}
