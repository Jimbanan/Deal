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
//
//
//
//
//
//
//CreditDTO(amount=56202.00, term=20, monthlyPayment=3300.1814400, rate=19, psk=66003.6288000, isInsuranceEnabled=false, isSalaryClient=false, paymentSchedule=[PaymentScheduleElement(number=1, date=2022-07-15, totalPayment=3300.1814400, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=62703.4473600), PaymentScheduleElement(number=2, date=2022-08-15, totalPayment=6600.3628800, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=59403.2659200), PaymentScheduleElement(number=3, date=2022-09-15, totalPayment=9900.5443200, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=56103.0844800), PaymentScheduleElement(number=4, date=2022-10-15, totalPayment=13200.7257600, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=52802.9030400), PaymentScheduleElement(number=5, date=2022-11-15, totalPayment=16500.9072000, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=49502.7216000), PaymentScheduleElement(number=6, date=2022-12-15, totalPayment=19801.0886400, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=46202.5401600), PaymentScheduleElement(number=7, date=2023-01-15, totalPayment=23101.2700800, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=42902.3587200), PaymentScheduleElement(number=8, date=2023-02-15, totalPayment=26401.4515200, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=39602.1772800), PaymentScheduleElement(number=9, date=2023-03-15, totalPayment=29701.6329600, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=36301.9958400), PaymentScheduleElement(number=10, date=2023-04-15, totalPayment=33001.8144000, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=33001.8144000), PaymentScheduleElement(number=11, date=2023-05-15, totalPayment=36301.9958400, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=29701.6329600), PaymentScheduleElement(number=12, date=2023-06-15, totalPayment=39602.1772800, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=26401.4515200), PaymentScheduleElement(number=13, date=2023-07-15, totalPayment=42902.3587200, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=23101.2700800), PaymentScheduleElement(number=14, date=2023-08-15, totalPayment=46202.5401600, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=19801.0886400), PaymentScheduleElement(number=15, date=2023-09-15, totalPayment=49502.7216000, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=16500.9072000), PaymentScheduleElement(number=16, date=2023-10-15, totalPayment=52802.9030400, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=13200.7257600), PaymentScheduleElement(number=17, date=2023-11-15, totalPayment=56103.0844800, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=9900.5443200), PaymentScheduleElement(number=18, date=2023-12-15, totalPayment=59403.2659200, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=6600.3628800), PaymentScheduleElement(number=19, date=2024-01-15, totalPayment=62703.4473600, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=3300.1814400), PaymentScheduleElement(number=20, date=2024-02-15, totalPayment=66003.6288000, interestPayment=490.0814400, debtPayment=3300.1814400, remainingDebt=0E-7)])
//
//
//
//
//
//
//
//
//
//
//
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