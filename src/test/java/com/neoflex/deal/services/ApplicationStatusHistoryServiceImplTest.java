//package com.neoflex.deal.services;
//
//import com.neoflex.deal.enums.Status;
//import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
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
//class ApplicationStatusHistoryServiceImplTest {
//
//    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
//
//    @Autowired
//    ApplicationStatusHistoryServiceImplTest(ApplicationStatusHistoryRepository applicationStatusHistoryRepository) {
//        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
//    }
//
//    @InjectMocks
//    private ApplicationStatusHistoryServiceImpl applicationStatusHistoryServiceImpl;
//
//    @Test
//    void addApplicationStatusHistory() {
//        ReflectionTestUtils.setField(applicationStatusHistoryServiceImpl, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);
//        Long count = applicationStatusHistoryRepository.count();
//        applicationStatusHistoryServiceImpl.addApplicationStatusHistory(Status.CC_DENIED);
//        Assertions.assertEquals(applicationStatusHistoryRepository.count(), count + 1);
//    }
//}