package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskVO {
    /**主键*/
    private Integer id;

    /**业务参数*/
    private String bussinessParam;

    /**cron表达式*/
    private String cron;
}
