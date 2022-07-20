package com.neoflex.deal.services;

import com.neoflex.deal.dto.CreditDTO;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.Application;
import com.neoflex.deal.models.PaymentSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final ApplicationServiceImpl applicationServiceImpl;
    private final PaymentScheduleServiceImpl paymentScheduleImpl;

    @Override
    public void updateCredit(CreditDTO creditDTO, Long applicationId) {

        List<PaymentSchedule> paymentSchedules = new ArrayList<>();
        log.info("updateCredit() - void:  List<PaymentSchedule> paymentSchedules - Создан");

        for (int i = 0; i < creditDTO.getPaymentSchedule().size(); i++) {
            PaymentSchedule paymentSchedule = PaymentSchedule.builder()
                    .number(creditDTO.getPaymentSchedule().get(i).getNumber())
                    .date(creditDTO.getPaymentSchedule().get(i).getDate())
                    .totalPayment(creditDTO.getPaymentSchedule().get(i).getTotalPayment())
                    .interestPayment(creditDTO.getPaymentSchedule().get(i).getInterestPayment())
                    .debtPayment(creditDTO.getPaymentSchedule().get(i).getDebtPayment())
                    .remainingDebt(creditDTO.getPaymentSchedule().get(i).getRemainingDebt())
                    .build();

            paymentSchedules.add(paymentScheduleImpl.addPaymentScheduleRepository(paymentSchedule));
        }
        log.info("updateCredit() - void:  List<PaymentSchedule> paymentSchedules - Заполнен и добавлен в БД");

        Application application = applicationServiceImpl.getApplication(applicationId);
        application.setStatus(Status.APPROVED);
        application.getCredit().setAmount(creditDTO.getAmount());
        application.getCredit().setTerm(creditDTO.getTerm());
        application.getCredit().setMonthlyPayment(creditDTO.getMonthlyPayment());
        application.getCredit().setRate(creditDTO.getRate());
        application.getCredit().setPsk(creditDTO.getPsk());
        application.getCredit().getAddServices().setIsInsuranceEnabled(creditDTO.getIsInsuranceEnabled());
        application.getCredit().getAddServices().setIsSalaryClient(creditDTO.getIsSalaryClient());
        application.getCredit().setPaymentSchedule(paymentSchedules);
        applicationServiceImpl.updateApplication(application, Status.APPROVED);
        log.info("updateCredit() - void: Информация о Application обновлена в БД");

    }


}
