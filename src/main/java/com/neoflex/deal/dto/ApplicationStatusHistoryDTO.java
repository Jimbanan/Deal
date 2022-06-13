package com.neoflex.deal.dto;

import com.neoflex.deal.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Сущность истории статусов")
public class ApplicationStatusHistoryDTO {

    private Status status;

    private LocalDateTime time;

}
