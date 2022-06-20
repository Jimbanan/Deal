package com.neoflex.deal.models;

import com.neoflex.deal.enums.CreditStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal amount; // (Сумма)

    @Column
    private Integer term; // (Срок)

    @Column
    private BigDecimal monthlyPayment; // (Ежемесячный платеж)

    @Column
    private BigDecimal rate; // (Процентная ставка)

    @Column
    private BigDecimal psk; // (Полная стоимость кредита)

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "addServices_id", unique = true, updatable = false)
    private AddServices addServices;// (доп услуги)

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentSchedules_id")
    private List<PaymentSchedule> paymentSchedule; //(График платежей)

    @Column
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus; //(Статус кредита)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "credit")
    @ToString.Exclude
    public Application application;
}