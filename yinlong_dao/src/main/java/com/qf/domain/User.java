package com.qf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    @Autowired
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String gender;
    private Integer flag;
    private Integer role;
    private String code;

}
