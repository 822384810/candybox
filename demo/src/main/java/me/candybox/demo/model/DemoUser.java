package me.candybox.demo.model;

import lombok.Data;

@Data
public class DemoUser {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
