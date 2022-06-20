package com.neoflex.deal.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude="id")
@EqualsAndHashCode(exclude="id")
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String series;

    @Column
    private String number;

    @Column
    private LocalDate issueDate;

    @Column
    private String issueBranch;

    public Passport(String series, String number) {
        this.series = series;
        this.number = number;
    }

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = CascadeType.ALL,optional = false, mappedBy = "passport")
    public Client client;
}