package com.neoflex.deal.dto;

import com.neoflex.deal.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность заявки")
public class ApplicationDTO {

    private Long id;
    private ClientDTO clientDTO;
    private CreditDTO creditDTO;
    private Status status;
    private LocalDate creationDate;
    private String appliedOffer;
    private LocalDate signDate;
    private Integer sesCode;
    private List<ApplicationStatusHistoryDTO> statusHistoryDTO;

}
