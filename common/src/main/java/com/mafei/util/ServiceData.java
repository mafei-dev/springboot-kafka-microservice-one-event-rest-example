package com.mafei.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
  @Author mafei
  @Created 7/21/2021 6:52 AM  
*/
@Data
@NoArgsConstructor
public class ServiceData {
    private String service;
    private Integer rolledCount = 0;

    public ServiceData(String service) {
        this.service = service;
    }
}
