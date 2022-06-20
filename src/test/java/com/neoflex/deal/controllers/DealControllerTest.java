package com.neoflex.deal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflex.deal.dto.EmploymentDTO;
import com.neoflex.deal.dto.FinishRegistrationRequestDTO;
import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.dto.LoanOfferDTO;
import com.neoflex.deal.enums.EmploymentStatus;
import com.neoflex.deal.enums.Genders;
import com.neoflex.deal.enums.MaritalStatus;
import com.neoflex.deal.enums.Position;
import com.neoflex.deal.models.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DealControllerTest {

    @MockBean
    private DealController dealController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void offersDeal() throws Exception {
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoanApplicationRequestDTO.builder()
                                .amount(BigDecimal.valueOf(10000))
                                .term(20)
                                .firstName("Николай")
                                .lastName("Козьяков")
                                .middleName("Николаевич")
                                .email("uservice371@mail.ru")
                                .birthdate(LocalDate.of(1980, 9, 29))
                                .passportSeries("1234")
                                .passportNumber("123456")
                                .applicationId(1L)
                                .build())))
                .andExpect(status().isOk());
    }

    @Test
    void offers() throws Exception {
        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoanOfferDTO.builder()
                                .applicationId(1L)
                                .requestedAmount(BigDecimal.valueOf(10000))
                                .totalAmount(BigDecimal.valueOf(10000))
                                .term(6)
                                .monthlyPayment(BigDecimal.valueOf(10000))
                                .rate(BigDecimal.valueOf(20))
                                .isInsuranceEnabled(true)
                                .isSalaryClient(true)
                                .build())))
                .andExpect(status().isOk());
    }

    @Test
    void calculate() throws Exception {

        mockMvc.perform(put("/deal/calculate/{applicationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FinishRegistrationRequestDTO.builder()
                                .genders(Genders.MALE)
                                .maritalStatus(MaritalStatus.MARRIED)
                                .dependentAmount(1)
                                .passportIssueDate(LocalDate.of(2002, 9, 29))
                                .passportIssueBrach("ТЕСТ")
                                .employment(EmploymentDTO.builder()
                                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                                        .employerINN("121212")
                                        .salary(BigDecimal.valueOf(10000))
                                        .position(Position.TOP_MANAGER)
                                        .workExperienceTotal(30)
                                        .workExperienceCurrent(5).build())
                                .account("132131241241234124")
                                .build())))
                .andExpect(status().isOk());
    }
}