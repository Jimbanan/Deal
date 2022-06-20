package com.neoflex.deal.feignclient;

import com.neoflex.deal.dto.CreditDTO;
import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.dto.LoanOfferDTO;
import com.neoflex.deal.dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "conveyorMC", url = "${conveyor.hostname}")
public interface ConveyorMC {

    @PostMapping("/conveyor/offers")
    List<LoanOfferDTO> getOffersListConveyor(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO);

    @PostMapping("/conveyor/calculation")
    CreditDTO offerConfirmConveyor(@RequestBody ScoringDataDTO scoringDataDTO);

}
