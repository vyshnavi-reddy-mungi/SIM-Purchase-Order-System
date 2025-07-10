package com.capgemini.telecom.ordersystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoDTO {

        @NotNull(message = "Account number cannot be null")
        @Pattern(regexp = "^[a-zA-Z0-9]{16}$", message = "invalid Account number entered")
        private String accountNo;

        @NotEmpty(message = "card type is required")
        @Size(min = 2, max = 10, message = "Card type must be between 2 and 10 characters")
        private String cardType;
}
