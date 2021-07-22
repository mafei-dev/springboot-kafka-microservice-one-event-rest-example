package com.mafei.util;

import com.mafei.annotation.RequestFrom;
import lombok.Getter;

import java.util.*;

/*
  @Author mafei
  @Created 7/21/2021 6:50 AM  
*/
@Getter
public class StepManager {
    private final HashSet<String> services = new HashSet<>();

    public void addSucceedServiceByRequestClass(final Class<?> requestClass) {
        services.add(requestClass.getAnnotation(RequestFrom.class).name());
    }

    public void addSucceedService(final String serviceName) {
        services.add(serviceName);
    }
}
