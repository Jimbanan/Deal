package com.neoflex.deal.dto;

import com.neoflex.deal.enums.Genders;
import com.neoflex.deal.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность клиента")
public class ClientDTO {

    private Long id;
    private String lastName;// (Фамилия)
    private String firstName;// (Имя)
    private String middleName;// (Отчество)
    private LocalDate birthdate;// (Дата рождения)
    private String email;// (Email адрес)
    private Genders gender;// (Пол)
    private MaritalStatus maritalStatus;// (Семейное положение)
    private Integer dependentAmount;// (Количество иждивенцев)
    private PassportDTO passport;
    private EmploymentDTO employment;// (Работа)
    private String account;// (Счет клиента)

}
