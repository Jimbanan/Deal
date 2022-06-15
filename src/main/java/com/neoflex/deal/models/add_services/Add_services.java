package com.neoflex.deal.models.add_services;

import com.neoflex.deal.models.credit.Credit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "add_services")
public class Add_services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isInsuranceEnabled; //(Страховка включена?)

    @Column
    private Boolean isSalaryClient; //(Зарплатный клиент?)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "addServices")
    public Credit credit;
}