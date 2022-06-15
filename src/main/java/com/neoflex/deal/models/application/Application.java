package com.neoflex.deal.models.application;

import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.applicationStatusHistory.ApplicationStatusHistory;
import com.neoflex.deal.models.client.Client;
import com.neoflex.deal.models.credit.Credit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private LocalDate creation_date; // (Дата создания)

    @Column
    private Long appliedOffer; // (Принятое предложение кредита)

    @Column
    private LocalDate sign_date; // (Дата подписания)

    @Column
    private String ses_code; // (Код ПЭП (Простая Электронная Подпись))

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private List<ApplicationStatusHistory> status_history; //(История изменения статусов)

    public Application(Client client) {
        this.client = client;
    }

}