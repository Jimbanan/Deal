package com.neoflex.deal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neoflex.deal.dto.*;
import com.neoflex.deal.enums.Theme;
import com.neoflex.deal.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final KafkaProducerServiceImpl kafkaProducerServiceImpl;
    private final SummaryAppInfoService summaryAppInfoService;

    public DealController(DealServiceImpl dealService,
                          ApplicationServiceImpl applicationService,
                          CreditServiceImpl creditServiceImpl,
                          KafkaProducerServiceImpl kafkaProducerServiceImpl,
                          SummaryAppInfoService summaryAppInfoService) {
        this.dealService = dealService;
        this.applicationService = applicationService;
        this.creditServiceImpl = creditServiceImpl;
        this.kafkaProducerServiceImpl = kafkaProducerServiceImpl;
        this.summaryAppInfoService = summaryAppInfoService;
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

        kafkaProducerServiceImpl.send("finish-registration", Theme.FINISH_REGISTRATION, loanOfferDTO.getApplicationId());

    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Расчет данных по кредитному предложению + Добавление конечных данных в бд")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @PathVariable Long applicationId) throws JsonProcessingException {

        System.out.println(finishRegistrationRequestDTO.getPassportIssueBranch());
        System.out.println(finishRegistrationRequestDTO.getPassportIssueDate());

        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(finishRegistrationRequestDTO, applicationId);
        CreditDTO creditDTO = dealService.offerConfirm(scoringDataDTO);
        creditServiceImpl.updateCredit(creditDTO, applicationId);

        kafkaProducerServiceImpl.send("create-documents", Theme.CREATE_DOCUMENTS, applicationId);

    }

    @PostMapping("/document/{applicationId}/send")
    @Operation(description = "Запрос на отправку документов")
    public void sendDocs(@PathVariable Long applicationId) throws JsonProcessingException {

        kafkaProducerServiceImpl.send("send-documents", Theme.SEND_DOCUMENTS, applicationId);

    }

    @PostMapping("/document/{applicationId}/sign")
    @Operation(description = "Запрос на подписание документов")
    public void singDocs(@PathVariable Long applicationId) throws JsonProcessingException {
        kafkaProducerServiceImpl.send("send-ses", Theme.SEND_SES, applicationId);
    }

    @PostMapping("/document/{applicationId}/code")
    @Operation(description = "Подписание документов")
    public ResponseEntity<?> receiveSesCode(@PathVariable Long applicationId,
                                            @RequestBody Integer sesCode) throws JsonProcessingException {

        Integer sesCodeApplication = Integer.valueOf(applicationService.getApplication(applicationId).getSesCode());

        if (sesCodeApplication.equals(sesCode)) {
            kafkaProducerServiceImpl.send("credit-issued", Theme.CREDIT_ISSUED, applicationId);
        } else {
            kafkaProducerServiceImpl.send("application-denied", Theme.APPLICATION_DENIED, applicationId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/application/{applicationId}")
    @Operation(description = "DossierMC)")
    public ResponseEntity<SummaryAppInfoDTO> getApplication(@PathVariable Long applicationId) {
        SummaryAppInfoDTO summaryAppInfoDTO = summaryAppInfoService.getSummaryAppInfo(applicationId);
        return new ResponseEntity<>(summaryAppInfoDTO, HttpStatus.OK);
    }
}