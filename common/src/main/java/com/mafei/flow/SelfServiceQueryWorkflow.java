package com.mafei.flow;

import com.mafei.util.StepManager;

import java.io.IOException;

/*
  @Author mafei
  @Created 7/20/2021 2:06 PM  
*/
public interface SelfServiceQueryWorkflow<T> {
    Object process(T aggregate, StepManager stepManager, Object... data) throws Exception;

    void publishError(T aggregate,StepManager stepManager, Object... data) throws IOException;
}
