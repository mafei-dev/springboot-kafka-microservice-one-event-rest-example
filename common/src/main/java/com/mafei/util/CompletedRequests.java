package com.mafei.util;

import lombok.*;

import java.util.Set;

/*
  @Author mafei
  @Created 7/21/2021 8:20 AM  
*/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CompletedRequests {
    private Set<String> services;
}
