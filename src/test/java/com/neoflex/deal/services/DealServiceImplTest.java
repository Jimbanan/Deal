//package com.neoflex.deal.services;//package com.neoflex.conveyor.services;
//
//import com.neoflex.deal.dto.*;
//import com.neoflex.deal.enums.*;
//import com.neoflex.deal.repository.AddServicesRepository;
//import com.neoflex.deal.models.AddServices;
//import com.neoflex.deal.models.Application;
//import com.neoflex.deal.repository.ApplicationRepository;
//import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
//import com.neoflex.deal.models.Client;
//import com.neoflex.deal.repository.ClientRepository;
//import com.neoflex.deal.models.Credit;
//import com.neoflex.deal.repository.CreditRepository;
//import com.neoflex.deal.repository.EmploymentRepository;
//import com.neoflex.deal.models.Passport;
//import com.neoflex.deal.repository.PassportRepository;
//import com.neoflex.deal.repository.PaymentScheduleRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.transaction.Transactional;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//
//@SpringBootTest
////@SpringBootApplication(exclude = DealServiceImpl.class)
//@Transactional
//class DealServiceImplTest {
//
//    @Autowired
//    private ClientRepository clientRepository;
//    @Autowired
//    private PassportRepository passportRepository;
//    @Autowired
//    private ApplicationRepository applicationRepository;
//    @Autowired
//    private CreditRepository creditRepository;
//    @Autowired
//    private AddServicesRepository addServesRepository;
//    @Autowired
//    private EmploymentRepository employmentRepository;
//    @Autowired
//    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
//    @Autowired
//    private PaymentScheduleRepository paymentScheduleRepository;
//
//    //    @InjectMocks
////    @Autowired
////    private DealServiceImpl dealService;
//
//    //Или так
//    private DealServiceImpl dealService = new DealServiceImpl(clientRepository,
//            passportRepository,
//            applicationRepository,
//            creditRepository,
//            addServesRepository,
//            employmentRepository,
//            applicationStatusHistoryRepository,
//            paymentScheduleRepository);
//
//
////    @InjectMocks
////    @Autowired
////    @MockBean
////    private DealServiceImpl dealService;
//
////    @Test
////    void addClient() {
////        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
////        ReflectionTestUtils.setField(dealService, "clientRepository", clientRepository);
////        ReflectionTestUtils.setField(dealService, "passportRepository", passportRepository);
////        ReflectionTestUtils.setField(dealService, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);
////        Long id = dealService.addClient(LoanApplicationRequestDTO.builder()
////                .amount(BigDecimal.valueOf(56202))
////                .term(20)
////                .firstName("TEST")
////                .lastName("Козьяков")
////                .middleName("Николаевич")
////                .email("uservice371@mail.ru")
////                .birthdate(LocalDate.of(1980, 9, 29))
////                .passportSeries("1234")
////                .passportNumber("123456")
////                .build());
////        Assertions.assertNotNull(id);
////    }
//
//    @Test
//    void addOffer() {
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//        ReflectionTestUtils.setField(dealService, "creditRepository", creditRepository);
//        ReflectionTestUtils.setField(dealService, "addServesRepository", addServesRepository);
//
//        Long count = applicationRepository.count();
//        Long id = applicationRepository.findTopByOrderByIdDesc().getId();
//
//        dealService.addOffer(LoanOfferDTO.builder()
//                .applicationId(id)
//                .requestedAmount(BigDecimal.valueOf(149999))
//                .totalAmount(BigDecimal.valueOf(140000))
//                .term(6)
//                .monthlyPayment(BigDecimal.valueOf(14000))
//                .rate(BigDecimal.valueOf(14))
//                .isInsuranceEnabled(true)
//                .isSalaryClient(true).build());
//
//        Assertions.assertNotEquals(applicationRepository.count(), count + 1);
//    }
//
//    @Test
//    void createScoringDataDTO() {
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//        ReflectionTestUtils.setField(dealService, "employmentRepository", employmentRepository);
//
//        Long applicationId = applicationRepository.findTopByOrderByIdDesc().getId();
//        System.out.println(applicationId);
//
//        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(FinishRegistrationRequestDTO.builder()
//                .genders(Genders.MALE)
//                .maritalStatus(MaritalStatus.MARRIED)
//                .dependentAmount(1)
//                .passportIssueDate(LocalDate.of(2002, 9, 29))
//                .passportIssueBrach("ТЕСТ")
//                .employment(EmploymentDTO.builder()
//                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
//                        .employerINN("121212")
//                        .salary(BigDecimal.valueOf(10000))
//                        .position(Position.TOP_MANAGER)
//                        .workExperienceTotal(30)
//                        .workExperienceCurrent(5).build())
//                .account("132131241241234124")
//                .build(), applicationId);
//        ScoringDataDTO scoringDataDTOTest = new ScoringDataDTO();
//        Assertions.assertEquals(scoringDataDTO.equals(scoringDataDTOTest), false);
//    }
//
//    @Test
//    void updateCredit() {
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//        ReflectionTestUtils.setField(dealService, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);
//        ReflectionTestUtils.setField(dealService, "paymentScheduleRepository", paymentScheduleRepository);
//
//        Long applicationId = applicationRepository.findTopByOrderByIdDesc().getId();
//
//        dealService.updateCredit(CreditDTO.builder()
//                .amount(BigDecimal.valueOf(100000))
//                .term(6)
//                .monthlyPayment(BigDecimal.valueOf(100000))
//                .rate(BigDecimal.valueOf(10))
//                .psk(BigDecimal.valueOf(10))
//                .isInsuranceEnabled(true)
//                .isSalaryClient(true)
//                .paymentSchedule(Arrays.asList(PaymentScheduleElement.builder()
//                        .number(1)
//                        .date(LocalDate.now())
//                        .totalPayment(BigDecimal.valueOf(10000))
//                        .interestPayment(BigDecimal.valueOf(10000))
//                        .debtPayment(BigDecimal.valueOf(10000))
//                        .remainingDebt(BigDecimal.valueOf(10000)).build())).build(), applicationId);
//        Credit creditTest = new Credit();
//
//        Long creditId = creditRepository.findTopByOrderByIdDesc().getId();
//        Credit credit = creditRepository.findById(creditId).orElseThrow(()
//                -> new NoSuchElementException("with id='" + creditId + "' does not exist"));
//
//        Assertions.assertEquals(credit.getMonthlyPayment().equals(creditTest.getMonthlyPayment()), false);
//    }
//
//    @Test
//    void getApplication() {
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//        Long id = applicationRepository.findTopByOrderByIdDesc().getId();
//        Application application = dealService.getApplication(id);
//        Assertions.assertNotNull(application);
//
//    }
//
//    @Test
//    void updateApplication() {
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//
//        Application application = applicationRepository.findTopByOrderByIdDesc();
//        application.setStatus(Status.DOCUMENT_SIGNED);
//
//        dealService.updateApplication(application);
//
//        Application applicationTest = applicationRepository.findTopByOrderByIdDesc();
//
//        Assertions.assertEquals(applicationTest.getStatus(), application.getStatus());
//    }
//
//    @Test
//    void savePassport() {
//        ReflectionTestUtils.setField(dealService, "passportRepository", passportRepository);
//        Long count = passportRepository.count();
//        dealService.savePassport(LoanApplicationRequestDTO.builder().build());
//        Assertions.assertEquals(passportRepository.count(), count + 1);
//    }
//
//    @Test
//    void saveClient() {
//        ReflectionTestUtils.setField(dealService, "clientRepository", clientRepository);
//        Long count = clientRepository.count();
//        dealService.saveClient(LoanApplicationRequestDTO.builder().build(), Passport.builder().build());
//        Assertions.assertEquals(clientRepository.count(), count + 1);
//    }
//
//    @Test
//    void saveEmployment() {
//        ReflectionTestUtils.setField(dealService, "employmentRepository", employmentRepository);
//
//        Long count = employmentRepository.count();
//        dealService.saveEmployment(FinishRegistrationRequestDTO.builder()
//                .genders(Genders.MALE)
//                .maritalStatus(MaritalStatus.MARRIED)
//                .dependentAmount(1)
//                .passportIssueDate(LocalDate.of(2002, 9, 29))
//                .passportIssueBrach("ТЕСТ")
//                .employment(EmploymentDTO.builder()
//                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
//                        .employerINN("121212")
//                        .salary(BigDecimal.valueOf(10000))
//                        .position(Position.TOP_MANAGER)
//                        .workExperienceTotal(30)
//                        .workExperienceCurrent(5).build())
//                .account("132131241241234124")
//                .build());
//        Assertions.assertEquals(employmentRepository.count(), count + 1);
//    }
//
//    @Test
//    void addApplicationStatusHistory() {
//        ReflectionTestUtils.setField(dealService, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);
//        Long count = applicationStatusHistoryRepository.count();
//        dealService.addApplicationStatusHistory(Status.PREAPPROVAL);
//        Assertions.assertEquals(applicationStatusHistoryRepository.count(), count + 1);
//    }
//
//    @Test
//    void saveApplication() {
//        ReflectionTestUtils.setField(dealService, "applicationRepository", applicationRepository);
//        ReflectionTestUtils.setField(dealService, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);
//
//        Long id = clientRepository.findTopByOrderByIdDesc().getId();
//        Client client = clientRepository.findById(id).orElseThrow(()
//                -> new NoSuchElementException("with id='" + id + "' does not exist"));
//
//        try {
//            dealService.saveApplication(client, Status.PREAPPROVAL);
//        } catch (DataIntegrityViolationException e) {
//            Assertions.assertEquals("Expected exception message",
//                    e.getMessage());
//            return;
//        }
//
//    }
//
//    @Test
//    void addAddServices() {
//        ReflectionTestUtils.setField(dealService, "addServesRepository", addServesRepository);
//        Long count = addServesRepository.count();
//        dealService.addAddServices(LoanOfferDTO.builder().build());
//        Assertions.assertEquals(addServesRepository.count(), count + 1);
//    }
//
//    @Test
//    void addCredit() {
//        ReflectionTestUtils.setField(dealService, "creditRepository", creditRepository);
//        Long count = creditRepository.count();
//        dealService.addCredit(LoanOfferDTO.builder().build(),
//                AddServices.builder().build());
//        Assertions.assertEquals(creditRepository.count(), count + 1);
//    }
//
//}