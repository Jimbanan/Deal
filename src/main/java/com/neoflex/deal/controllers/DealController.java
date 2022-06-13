package com.neoflex.deal.controllers;

import com.neoflex.deal.dto.*;
import com.neoflex.deal.services.DealServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.validation.Valid;
import java.util.List;

@RequestMapping(("/deal"))
@RestController
@Tag(name = "ConveyorController", description = "Кредитный конвейер")
public class DealController {

    @Autowired
    DealServiceImpl dealService;

    @PostMapping("/application")
    @Operation(description = "Формирование списка кредитных предложение + Добавление первичных данных в БД")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        loanApplicationRequestDTO.setApplicationId(dealService.addClient(loanApplicationRequestDTO));
        RestTemplate restTemplate = new RestTemplate();
        String uri_Offers = "http://localhost:8080/conveyor/offers";
        return restTemplate.postForObject(uri_Offers, loanApplicationRequestDTO, List.class);
    }

    @PutMapping("/offer")
    @Operation(description = "Добавление полученного офера в БД")
    public void offers(@RequestBody LoanOfferDTO loanOfferDTO) {
        dealService.addOffer(loanOfferDTO);
    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Расчет данных по кредитному предложению + Добавление конечных данных в бд")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @PathVariable Long applicationId) {
        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(finishRegistrationRequestDTO, applicationId);
        RestTemplate restTemplate = new RestTemplate();
        String uri_Calculate = "http://localhost:8080/conveyor/calculation";
        CreditDTO creditDTO = restTemplate.postForObject(uri_Calculate, scoringDataDTO, CreditDTO.class);
        dealService.updateCredit(creditDTO, applicationId);
    }


}
