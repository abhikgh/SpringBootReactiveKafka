package com.example.SpringBootReactiveKafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Employee {

    private String firsrName;
    private String lastName;
}
