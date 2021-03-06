package com.neoflex.deal.models.employment;

import com.neoflex.deal.enums.EmploymentStatus;
import com.neoflex.deal.enums.Position;
import com.neoflex.deal.models.client.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employment")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus; // (Рабочий статус)

    @Column
    private BigDecimal salary;// (зарплата)

    @Column
    private String EmployerINN;

    @Column
    @Enumerated(EnumType.STRING)
    private Position position;// (должность)

    @Column
    private Integer workExperienceTotal;// (общий опыт работы)

    @Column
    private Integer workExperienceCurrent;// (опыт работы на текущем месте)

    //    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "employment")
    public Client client;


}