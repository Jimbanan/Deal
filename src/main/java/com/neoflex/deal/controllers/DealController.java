package com.neoflex.deal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neoflex.deal.dto.*;
import com.neoflex.deal.enums.Theme;
import com.neoflex.deal.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(("/deal"))
@RestController
@Slf4j
@Tag(name = "DealController", description = "Кредитный конвейер")
public class DealController {

    private final DealServiceImpl dealService;
    private final ApplicationServiceImpl applicationService;
    private final CreditServiceImpl creditServiceImpl;
    private final KafkaProducerServiceImpl kafkaProducerServiceImpl;
    private final SummaryAppInfoServiceImpl summaryAppInfoServiceImpl;

    public DealController(DealServiceImpl dealService,
                          ApplicationServiceImpl applicationService,
                          CreditServiceImpl creditServiceImpl,
                          KafkaProducerServiceImpl kafkaProducerServiceImpl,
                          SummaryAppInfoServiceImpl summaryAppInfoServiceImpl) {
        this.dealService = dealService;
        this.applicationService = applicationService;
        this.creditServiceImpl = creditServiceImpl;
        this.kafkaProducerServiceImpl = kafkaProducerServiceImpl;
        this.summaryAppInfoServiceImpl = summaryAppInfoServiceImpl;
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
        log.info("offers() - void: Сообщение отправлено по следующему топику: finish-registration");

    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Расчет данных по кредитному предложению + Добавление конечных данных в бд")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @PathVariable Long applicationId) throws JsonProcessingException {

        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(finishRegistrationRequestDTO, applicationId);
        CreditDTO creditDTO = dealService.offerConfirm(scoringDataDTO);
        creditServiceImpl.updateCredit(creditDTO, applicationId);

        kafkaProducerServiceImpl.send("create-documents", Theme.CREATE_DOCUMENTS, applicationId);
        log.info("calculate() - void: Сообщение отправлено по следующему топику: create-documents");

    }

    @PostMapping("/document/{applicationId}/send")
    @Operation(description = "Запрос на отправку документов")
    public void sendDocs(@PathVariable Long applicationId) throws JsonProcessingException {

        kafkaProducerServiceImpl.send("send-documents", Theme.SEND_DOCUMENTS, applicationId);
        log.info("sendDocs() - void: Сообщение отправлено по следующему топику: send-documents");

    }

    @PostMapping("/document/{applicationId}/sign")
    @Operation(description = "Запрос на подписание документов")
    public void singDocs(@PathVariable Long applicationId) throws JsonProcessingException {

        kafkaProducerServiceImpl.send("send-ses", Theme.SEND_SES, applicationId);
        log.info("singDocs() - void: Сообщение отправлено по следующему топику: send-ses");

    }

    @PostMapping("/document/{applicationId}/code")
    @Operation(description = "Подписание документов")
    public void receiveSesCode(@PathVariable Long applicationId,
                               @RequestBody Integer sesCode) throws JsonProcessingException {

        Integer sesCodeApplication = Integer.valueOf(applicationService.getApplication(applicationId).getSesCode());

        if (sesCodeApplication.equals(sesCode)) {
            log.info("receiveSesCode() - void: Введенный SESCode правильный. Кредит одобрен! ");
            kafkaProducerServiceImpl.send("credit-issued", Theme.CREDIT_ISSUED, applicationId);
            log.info("receiveSesCode() - void: Сообщение отправлено по следующему топику: credit-issued");

        } else {
            log.info("receiveSesCode() - void: Введенный SESCode неправильный. В кредите отказано!");
            kafkaProducerServiceImpl.send("application-denied", Theme.APPLICATION_DENIED, applicationId);
            log.info("receiveSesCode() - void: Сообщение отправлено по следующему топику: application-denied");
        }

    }


    @PostMapping("/application/{applicationId}")
    @Operation(description = "DossierMC)")
    public ResponseEntity<SummaryAppInfoDTO> getApplication(@PathVariable Long applicationId) {
        log.info("getApplication() - ResponseEntity<SummaryAppInfoDTO>: Создание SummaryAppInfoDTO");
        SummaryAppInfoDTO summaryAppInfoDTO = summaryAppInfoServiceImpl.getSummaryAppInfo(applicationId);
        log.info("getApplication() - ResponseEntity<SummaryAppInfoDTO>: SummaryAppInfoDTO создан");
        return new ResponseEntity<>(summaryAppInfoDTO, HttpStatus.OK);
    }
}