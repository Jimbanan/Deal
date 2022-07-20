package com.neoflex.deal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность первоначальных данных пользователя")
public class LoanApplicationRequestDTO {

    @NotNull
    @DecimalMin(value = "10000", message = "Минимальная сумма кредита: 10000")
    private BigDecimal amount;

    @NotNull
    @Min(value = 6, message = "Минимальный срок кредита: 6 месяцев")
    private Integer term;

    @NotNull
    @Size(min = 2, max = 30, message = "Длина имени: 2-30 символов")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "Длина фамилии: 2-30 символов")
    private String lastName;

    @Size(min = 2, max = 30, message = "Длина отчества: 2-30 символов")
    private String middleName;

    @NotNull
    @Pattern(regexp = "^[\\w-.]{2,50}@([\\w-]+\\.)+[\\w-]{2,20}")
    private String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Дата рождения не должна быть больше текущей")
    private LocalDate birthdate;

    @NotNull
    @Size(min = 4, max = 4, message = "Длина серии паспорта: 4 символа")
    private String passportSeries;

    @NotNull
    @Size(min = 6, max = 6, message = "Длина номера паспорта: 6 символов")
    private String passportNumber;

    @JsonIgnoreProperties
    private Long applicationId;

}
