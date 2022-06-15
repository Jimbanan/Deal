package com.neoflex.deal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "paymentSchedule")
public class PaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer number;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal totalPayment;

    @Column
    private BigDecimal interestPayment;

    @Column
    private BigDecimal debtPayment;

    @Column
    private BigDecimal remainingDebt;

}