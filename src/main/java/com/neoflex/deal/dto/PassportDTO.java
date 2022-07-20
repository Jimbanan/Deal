package com.neoflex.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность паспорта")
public class PassportDTO {

    private Long id;
    private String series;
    private String number;
    private LocalDate issueDate;
    private String issueBranch;

}
