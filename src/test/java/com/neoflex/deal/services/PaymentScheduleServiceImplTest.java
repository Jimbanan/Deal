//package com.neoflex.deal.services;
//
//import com.neoflex.deal.models.PaymentSchedule;
//import com.neoflex.deal.repository.PaymentScheduleRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.transaction.Transactional;
//
//@Transactional
//@SpringBootTest
//class PaymentScheduleServiceImplTest {
//
//    private PaymentScheduleRepository paymentScheduleRepository;
//
//    @Autowired
//    PaymentScheduleServiceImplTest(PaymentScheduleRepository paymentScheduleRepository) {
//        this.paymentScheduleRepository = paymentScheduleRepository;
//    }
//
//    @InjectMocks
//    private PaymentScheduleServiceImpl paymentScheduleServiceImpl;
//
//    @Test
//    void addPaymentScheduleRepository() {
//        ReflectionTestUtils.setField(paymentScheduleServiceImpl, "paymentScheduleRepository", paymentScheduleRepository);
//        Long count = paymentScheduleRepository.count();
//        paymentScheduleServiceImpl.addPaymentScheduleRepository(PaymentSchedule.builder().build());
//        Assertions.assertEquals(paymentScheduleRepository.count(), count + 1);
//    }
//}