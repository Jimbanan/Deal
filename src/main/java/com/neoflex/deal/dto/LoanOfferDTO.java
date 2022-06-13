package com.neoflex.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность предложенного офера")
public class LoanOfferDTO {

    private Long applicationId; //Идентификатор
    private BigDecimal requestedAmount; //Запрошенная сумма
    private BigDecimal totalAmount; //Итого
    private Integer term; //Срок кредита
    private BigDecimal monthlyPayment; //Ежемесячная оплата
    private BigDecimal rate; //Ставка
    private Boolean isInsuranceEnabled; //Включено ли страхование
    private Boolean isSalaryClient; //Зарплатный ли клиент

}


