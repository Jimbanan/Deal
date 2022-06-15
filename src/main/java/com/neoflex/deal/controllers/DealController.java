package com.neoflex.deal.controllers;

import com.neoflex.deal.dto.*;
import com.neoflex.deal.services.ApplicationServiceImpl;
import com.neoflex.deal.services.CreditServiceImpl;
import com.neoflex.deal.services.DealServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(("/deal"))
@RestController
@Tag(name = "DealController", description = "Кредитный конвейер")
public class DealController {

    private final DealServiceImpl dealService;
    private final ApplicationServiceImpl applicationService;
    private final CreditServiceImpl creditServiceImpl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${conveyor.hostname}")
    String hostname;

    public DealController(DealServiceImpl dealService, ApplicationServiceImpl applicationService, CreditServiceImpl creditServiceImpl) {
        this.dealService = dealService;
        this.applicationService = applicationService;
        this.creditServiceImpl = creditServiceImpl;
    }

    @PostMapping("/application")
    @Operation(description = "Формирование списка кредитных предложение + Добавление первичных данных в БД")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        loanApplicationRequestDTO.setApplicationId(applicationService.addApplication(loanApplicationRequestDTO));
        String uri_Offers = hostname + "/conveyor/offers";
        return restTemplate.postForObject(uri_Offers, loanApplicationRequestDTO, List.class);
    }

    @PutMapping("/offer")
    @Operation(description = "Добавление полученного офера в БД")
    public void offers(@RequestBody LoanOfferDTO loanOfferDTO) {
        applicationService.addApplicationOffer(loanOfferDTO);
    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Расчет данных по кредитному предложению + Добавление конечных данных в бд")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @PathVariable Long applicationId) {

        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(finishRegistrationRequestDTO, applicationId);
        String uri_Calculate = hostname + "/conveyor/calculation";
        CreditDTO creditDTO = restTemplate.postForObject(uri_Calculate, scoringDataDTO, CreditDTO.class);
        creditServiceImpl.updateCredit(creditDTO, applicationId);
    }


}
