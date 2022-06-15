package com.neoflex.deal.models;

import com.neoflex.deal.enums.Genders;
import com.neoflex.deal.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String lastName;// (Фамилия)

    @Column
    private String firstName;// (Имя)

    @Column
    private String middleName;// (Отчество)

    @Column
    private LocalDate birthdate;// (Дата рождения)

    @Column
    private String email;// (Email адрес)

    @Column
    @Enumerated(EnumType.STRING)
    private Genders gender;// (Пол)

    @Column
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;// (Семейное положение)

    @Column
    private Integer dependentAmount;// (Количество иждивенцев)

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "passport_id", unique = true, nullable = false)
    private Passport passport;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", unique = true)
    private Employment employment;// (Работа)

    @Column
    private String account;// (Счет клиента)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "client")
    public Application application;
}