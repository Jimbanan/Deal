package com.neoflex.deal.services;

import com.neoflex.deal.dto.EmploymentDTO;
import com.neoflex.deal.dto.FinishRegistrationRequestDTO;
import com.neoflex.deal.enums.EmploymentStatus;
import com.neoflex.deal.enums.Genders;
import com.neoflex.deal.enums.MaritalStatus;
import com.neoflex.deal.enums.Position;
import com.neoflex.deal.repository.ApplicationRepository;
import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
import com.neoflex.deal.repository.PassportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DealServiceImplTest {

    @Autowired
    ApplicationServiceImpl applicationServiceImpl;
    @Autowired
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationStatusHistoryServiceImpl applicationStatusHistoryServiceImpl;

    @InjectMocks
    private DealServiceImpl dealServiceImpl;


    @Test
    void createScoringDataDTO() {
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);

        ReflectionTestUtils.setField(dealServiceImpl, "applicationServiceImpl", applicationServiceImpl);


        Assertions.assertNotNull(dealServiceImpl.createScoringDataDTO(FinishRegistrationRequestDTO.builder()
                .genders(Genders.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(1)
                .passportIssueDate(LocalDate.of(2002, 9, 29))
                .passportIssueBrach("Улица Пушкина - Дом Колотушкина")
                .employment(EmploymentDTO.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .employerINN("757838275")
                        .salary(BigDecimal.valueOf(10000))
                        .position(Position.TOP_MANAGER)
                        .workExperienceTotal(30)
                        .workExperienceCurrent(5)
                        .build())
                .account("40817810099910004312")
                .build(), applicationRepository.findTopByOrderByIdDesc().getId()));


    }
}