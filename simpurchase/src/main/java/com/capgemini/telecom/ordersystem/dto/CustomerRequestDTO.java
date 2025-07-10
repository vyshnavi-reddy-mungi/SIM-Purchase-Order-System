package com.capgemini.telecom.ordersystem.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    @NotNull(message = "first name shouldn't be null")
    private String firstName;

    @NotNull(message = "last name shouldn't be null")
    private String lastName;

    @Email(message = "invalid email address")
    private String email;

    @Min(18)
    @Max(80)
    private int age;
}
