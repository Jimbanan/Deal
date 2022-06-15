package com.neoflex.deal.models.credit;

import com.neoflex.deal.enums.Credit_status;
import com.neoflex.deal.models.add_services.Add_services;
import com.neoflex.deal.models.application.Application;
import com.neoflex.deal.models.paymentSchedule.PaymentSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Add_services addServices;// (доп услуги)

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentSchedules_id")
    private List<PaymentSchedule> paymentSchedule; //(График платежей)

    @Column
    @Enumerated(EnumType.STRING)
    private Credit_status creditStatus; //(Статус кредита)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "credit")
    public Application application;
}