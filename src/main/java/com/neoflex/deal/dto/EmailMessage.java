package com.neoflex.deal.dto;

import com.neoflex.deal.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMessage {
    private String address;
    private Theme theme;
    private Long applicationId;
}