package com.example.test.domain.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    private int seq;
    private String val;

    public Test(String val){
        this.val = val;
    }
}
