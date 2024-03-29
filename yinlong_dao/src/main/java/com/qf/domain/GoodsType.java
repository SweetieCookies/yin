package com.qf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsType implements Serializable {
    private  Integer id;
    private String name;
    private Integer level;
    private Integer Parent;
}
