package com.neoflex.deal.models.passport;

import com.neoflex.deal.models.client.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String passportSeries;

    @Column
    private String passportNumber;

    @Column
    private LocalDate passportIssueDate;

    @Column
    private String passportIssueBranch;

    public Passport(String passportSeries, String passportNumber) {
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = CascadeType.ALL,optional = false, mappedBy = "passport")
    public Client client;
}