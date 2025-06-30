package com.dailycodework.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ApiResonse {
    private String message;
    private Object data;
}
