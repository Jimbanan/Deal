package com.neoflex.deal.services;

import com.neoflex.deal.models.PaymentSchedule;
import com.neoflex.deal.repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentScheduleServiceImpl implements PaymentSchedulesService {

    private final PaymentScheduleRepository paymentScheduleRepository;

    public PaymentSchedule addPaymentScheduleRepository(PaymentSchedule paymentSchedule) {
        log.info("addPaymentScheduleRepository() - PaymentSchedule: Добавление платежа №{}", paymentSchedule.getNumber());
        return paymentScheduleRepository.save(paymentSchedule);
    }

}
