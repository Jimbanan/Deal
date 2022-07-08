package com.neoflex.deal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neoflex.deal.dto.*;
import com.neoflex.deal.enums.Theme;
import com.neoflex.deal.services.ApplicationServiceImpl;
import com.neoflex.deal.services.CreditServiceImpl;
import com.neoflex.deal.services.DealServiceImpl;
import com.neoflex.deal.services.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(("/deal"))
@RestController
@Tag(name = "DealController", description = "Кредитный конвейер")
public class DealController {

    private final DealServiceImpl dealService;
    private final ApplicationServiceImpl applicationService;
    private final CreditServiceImpl creditServiceImpl;
    private final KafkaProducerService kafkaProducerService;


    public DealController(DealServiceImpl dealService,
                          ApplicationServiceImpl applicationService,
                          CreditServiceImpl creditServiceImpl,
                          KafkaProducerService kafkaProducerService) {
        this.dealService = dealService;
        this.applicationService = applicationService;
        this.creditServiceImpl = creditServiceImpl;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/application")
    @Operation(description = "Формирование списка кредитных предложение + Добавление первичных данных в БД")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        loanApplicationRequestDTO.setApplicationId(applicationService.addApplication(loanApplicationRequestDTO));
        return dealService.getOffersList(loanApplicationRequestDTO);
    }

    @PutMapping("/offer")
    @Operation(description = "Добавление полученного офера в БД")
    public void offers(@RequestBody LoanOfferDTO loanOfferDTO) throws JsonProcessingException {
        applicationService.addApplicationOffer(loanOfferDTO);

        kafkaProducerService.send("finish-registration", Theme.FINISH_REGISTRATION, loanOfferDTO.getApplicationId());

    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Расчет данных по кредитному предложению + Добавление конечных данных в бд")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @PathVariable Long applicationId) {

        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(finishRegistrationRequestDTO, applicationId);
        CreditDTO creditDTO = dealService.offerConfirm(scoringDataDTO);
        creditServiceImpl.updateCredit(creditDTO, applicationId);
    }

}