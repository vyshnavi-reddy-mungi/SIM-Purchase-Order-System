package com.capgemini.telecom.ordersystem.config;

import com.capgemini.telecom.ordersystem.enums.SimCardStatus;
import com.capgemini.telecom.ordersystem.model.SimCard;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.context.properties.bind.BindException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimCardFieldSetMapper implements FieldSetMapper<SimCard> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public SimCard mapFieldSet(FieldSet fieldSet) throws BindException {
        SimCard simCard = new SimCard();

        simCard.setSimNumber(fieldSet.readString("sim_number"));
        simCard.setCustomerId(fieldSet.readString("customer_id"));
        simCard.setEstimatedCost(fieldSet.readDouble("estimated_cost"));
        simCard.setPlanId(fieldSet.readString("plan_id"));
        simCard.setStatus(SimCardStatus.valueOf(fieldSet.readString("status")));

        // Parse planStartDate
        String planStartDateStr = fieldSet.readString("plan_start_date");
        simCard.setPlanStartDate(planStartDateStr != null && !planStartDateStr.isEmpty()
                ? LocalDate.parse(planStartDateStr, DATE_FORMAT)
                : null);

        // Parse planEndDate
        String planEndDateStr = fieldSet.readString("plan_end_date");
        simCard.setPlanEndDate(planEndDateStr != null && !planEndDateStr.isEmpty()
                ? LocalDate.parse(planEndDateStr, DATE_FORMAT)
                : null);

        // Parse purchaseDate
        String purchaseDateStr = fieldSet.readString("purchase-date");
        simCard.setPurchaseDate(purchaseDateStr != null && !purchaseDateStr.isEmpty()
                ? LocalDateTime.parse(purchaseDateStr, DATETIME_FORMAT)
                : null);

        return simCard;
    }
}
