package com.neoflex.deal.models;

import com.neoflex.deal.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude="id")
@EqualsAndHashCode(exclude="id")
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "client_id", unique = true)
    private Client client; // (Клиент)

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", unique = true)
    private Credit credit; // (Кредит)

    @Column
    @Enumerated(EnumType.STRING)
    private Status status; // (Статус)

    @Column
    private LocalDate creationDate; // (Дата создания)

    @Column
    private Long appliedOffer; // (Принятое предложение кредита)

    @Column
    private LocalDate signDate; // (Дата подписания)

    @Column
    private String sesCode; // (Код ПЭП (Простая Электронная Подпись))

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private List<ApplicationStatusHistory> statusHistory; //(История изменения статусов)

}