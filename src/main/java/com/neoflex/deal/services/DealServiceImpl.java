package com.neoflex.deal.services;

import com.neoflex.deal.dto.*;
import com.neoflex.deal.enums.Credit_status;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.add_services.Add_serivesRepository;
import com.neoflex.deal.models.add_services.Add_services;
import com.neoflex.deal.models.application.Application;
import com.neoflex.deal.models.application.ApplicationRepository;
import com.neoflex.deal.models.applicationStatusHistory.ApplicationStatusHistory;
import com.neoflex.deal.models.applicationStatusHistory.ApplicationStatusHistoryRepository;
import com.neoflex.deal.models.client.Client;
import com.neoflex.deal.models.client.ClientRepository;
import com.neoflex.deal.models.credit.Credit;
import com.neoflex.deal.models.credit.CreditRepository;
import com.neoflex.deal.models.employment.Employment;
import com.neoflex.deal.models.employment.EmploymentRepository;
import com.neoflex.deal.models.passport.Passport;
import com.neoflex.deal.models.passport.PassportRepository;
import com.neoflex.deal.models.paymentSchedule.PaymentSchedule;
import com.neoflex.deal.models.paymentSchedule.PaymentScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private Add_serivesRepository addServesRepository;
    @Autowired
    private EmploymentRepository employmentRepository;
    @Autowired
    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

//    public DealServiceImpl(@Autowired ClientRepository clientRepository,
//                           @Autowired PassportRepository passportRepository,
//                           @Autowired ApplicationRepository applicationRepository,
//                           @Autowired CreditRepository creditRepository,
//                           @Autowired Add_serivesRepository addServesRepository,
//                           @Autowired EmploymentRepository employmentRepository,
//                           @Autowired ApplicationStatusHistoryRepository applicationStatusHistoryRepository,
//                           @Autowired PaymentScheduleRepository paymentScheduleRepository) {
//        this.clientRepository = clientRepository;
//        this.passportRepository = passportRepository;
//        this.applicationRepository = applicationRepository;
//        this.creditRepository = creditRepository;
//        this.addServesRepository = addServesRepository;
//        this.employmentRepository = employmentRepository;
//        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
//        this.paymentScheduleRepository = paymentScheduleRepository;
//    }


    @Override
    public Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("addClient() - Long: {}", saveApplication(saveClient(loanApplicationRequestDTO, savePassport(loanApplicationRequestDTO)), Status.PREAPPROVAL));
        Application application = saveApplication(saveClient(loanApplicationRequestDTO, savePassport(loanApplicationRequestDTO)), Status.PREAPPROVAL);
        return application.getId();
    }
    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {

        Application application = getApplication(loanOfferDTO.getApplicationId());
        application.setCredit(addCredit(loanOfferDTO, addAddServices(loanOfferDTO)));
        application.setAppliedOffer(loanOfferDTO.getApplicationId());
        application.setSign_date(LocalDate.now());
        applicationRepository.save(application);
        log.info("addOffer() - void: ???????????????????? ?? ?????????????????? ?????????? ?????????????????? ?? ???????? ????????????");
    }

    @Override
    public ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {

        Application application = getApplication(applicationId);
        application.getClient().setGender(finishRegistrationRequestDTO.getGenders());
        application.getClient().setMarital_status(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().getPassport().setPassportIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setPassportIssueBranch(finishRegistrationRequestDTO.getPassportIssueBrach());
        application.getClient().setEmployment(saveEmployment(finishRegistrationRequestDTO));
        application.getClient().setAccount(finishRegistrationRequestDTO.getAccount());
        updateApplication(application);
        log.info("createScoringDataDTO() - ScoringDataDTO: ???????????????????? ?? Application ?????????????????? ?? ????");

        return ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(application.getClient().getFirstName())
                .lastName(application.getClient().getLastName())
                .middleName(application.getClient().getMiddleName())
                .gender(application.getClient().getGender())
                .birthdate(application.getClient().getBirthdate())
                .passportSeries(application.getClient().getPassport().getPassportSeries())
                .passportNumber(application.getClient().getPassport().getPassportNumber())
                .passportIssueDate(application.getClient().getPassport().getPassportIssueDate())
                .passportIssueBranch(application.getClient().getPassport().getPassportIssueBranch())
                .maritalStatus(application.getClient().getMarital_status())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(finishRegistrationRequestDTO.getEmployment())
                .account(application.getClient().getAccount())
                .isInsuranceEnabled(application.getCredit().getAddServices().getIs_insurance_enabled())
                .isSalaryClient(application.getCredit().getAddServices().getIs_salary_client())
                .build();
    }

    @Override
    public void updateCredit(CreditDTO creditDTO, Long applicationId) {
        List<PaymentSchedule> paymentSchedules = new ArrayList<>();
        log.info("updateCredit() - void:  List<PaymentSchedule> paymentSchedules - ????????????");

        for (int i = 0; i < creditDTO.getPaymentSchedule().size(); i++) {
            paymentSchedules.add(paymentScheduleRepository.save(PaymentSchedule.builder()
                    .number(creditDTO.getPaymentSchedule().get(i).getNumber())
                    .date(creditDTO.getPaymentSchedule().get(i).getDate())
                    .totalPayment(creditDTO.getPaymentSchedule().get(i).getTotalPayment())
                    .interestPayment(creditDTO.getPaymentSchedule().get(i).getInterestPayment())
                    .debtPayment(creditDTO.getPaymentSchedule().get(i).getDebtPayment())
                    .remainingDebt(creditDTO.getPaymentSchedule().get(i).getRemainingDebt())
                    .build()));
        }
        log.info("updateCredit() - void:  List<PaymentSchedule> paymentSchedules - ???????????????? ?? ???????????????? ?? ????");

        Application application = getApplication(applicationId);
        application.setStatus(Status.APPROVED);
        application.getCredit().setAmount(creditDTO.getAmount());
        application.getCredit().setTerm(creditDTO.getTerm());
        application.getCredit().setMonthlyPayment(creditDTO.getMonthlyPayment());
        application.getCredit().setRate(creditDTO.getRate());
        application.getCredit().setPsk(creditDTO.getPsk());
        application.getCredit().getAddServices().setIs_insurance_enabled(creditDTO.getIsInsuranceEnabled());
        application.getCredit().getAddServices().setIs_salary_client(creditDTO.getIsSalaryClient());
        application.getCredit().setPayment_schedule(paymentSchedules);
        updateApplication(application, Status.APPROVED);
        log.info("updateCredit() - void: ???????????????????? ?? Application ?????????????????? ?? ????");

    }

    public Application getApplication(Long applicationId) {
        log.info("getApplication() - Application: ???????????? ?????? application.id = {}", applicationId);
        return applicationRepository.findById(applicationId).orElseThrow(()
                -> new NoSuchElementException("with id='" + applicationId + "' does not exist"));
    }

    @Transactional
    public void updateApplication(Application application, Status status) {
        List<ApplicationStatusHistory> list = new ArrayList<>();
        list.add(addApplicationStatusHistory(status));

        application.getStatus_history().add(list.get(0));
        log.info("updateApplication() - void: ???????????????????? ?? application.status_history ??????????????????");

        applicationRepository.save(application);
        log.info("updateApplication() - void: ???????????????????? ?? Application ?????????????????? ?? ????");
    }

    public void updateApplication(Application application) {
        applicationRepository.save(application);
        log.info("updateApplication() - void: ???????????????????? ?? Application ?????????????????? ?? ????");
    }

    public Passport savePassport(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Passport passport = new Passport(loanApplicationRequestDTO.getPassportSeries(), loanApplicationRequestDTO.getPassportNumber());
        log.info("savePassport() - Passport: ???????????????????? ?? Passport ?????????????????? ?? ????");
        return passportRepository.save(passport);
    }

    public Client saveClient(LoanApplicationRequestDTO loanApplicationRequestDTO, Passport passport) {
        log.info("saveClient() - Client: ???????????????????? ?? Client ?????????????????? ?? ????");
        return clientRepository.save(Client.builder()
                .lastName(loanApplicationRequestDTO.getLastName())
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleName(loanApplicationRequestDTO.getMiddleName())
                .birthdate(loanApplicationRequestDTO.getBirthdate())
                .email(loanApplicationRequestDTO.getEmail())
                .passport(passport)
                .build());
    }

    public Employment saveEmployment(FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        log.info("saveEmployment() - Employment: ???????????????????? ?? Employment ?????????????????? ?? ????");
        return employmentRepository.save(Employment.builder()
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .EmployerINN(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .workExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .workExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .build());
    }

    public ApplicationStatusHistory addApplicationStatusHistory(Status status) {
        log.info("addApplicationStatusHistory() - ApplicationStatusHistory: ???????????????????? ?? ApplicationStatusHistory ?????????????????? ?? ????");
        return applicationStatusHistoryRepository.save(ApplicationStatusHistory.builder()
                .status(status)
                .time(LocalDateTime.now())
                .build());
    }

    public Application saveApplication(Client client, Status status) {
        log.info("saveApplication() - Long: ???????????????????? ?? Application ?????????????????? ?? ????");
        return applicationRepository.save(Application.builder()
                .client(client)
                .creation_date(LocalDate.now())
                .status(status)
                .status_history(Arrays.asList(addApplicationStatusHistory(status)))
                .build());
    }

    public Add_services addAddServices(LoanOfferDTO loanOfferDTO) {
        log.info("addAddServices() - Add_services: ???????????????????? ?? Add_services ?????????????????? ?? ????");
        return addServesRepository.save(Add_services.builder()
                .is_insurance_enabled(loanOfferDTO.getIsInsuranceEnabled())
                .is_salary_client(loanOfferDTO.getIsSalaryClient())
                .build());
    }

    public Credit addCredit(LoanOfferDTO loanOfferDTO, Add_services addServices) {
        log.info("addCredit() - Credit: ???????????????????? ?? Credit ?????????????????? ?? ????");
        return creditRepository.save(Credit.builder()
                .amount(loanOfferDTO.getRequestedAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .psk(loanOfferDTO.getTotalAmount())
                .addServices(addServices)
                .credit_status(Credit_status.CALCULATED)
                .build());
    }
}
