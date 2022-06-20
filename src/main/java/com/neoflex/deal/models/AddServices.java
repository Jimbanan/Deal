package com.neoflex.deal.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "addServices")
public class AddServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isInsuranceEnabled; //(Страховка включена?)

    @Column
    private Boolean isSalaryClient; //(Зарплатный клиент?)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "addServices")
    @ToString.Exclude
    public Credit credit;
}