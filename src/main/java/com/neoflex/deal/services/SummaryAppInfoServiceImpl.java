package com.neoflex.deal.services;

import com.neoflex.deal.dto.EmploymentDTO;
import com.neoflex.deal.dto.PaymentScheduleElement;
import com.neoflex.deal.dto.SummaryAppInfoDTO;
import com.neoflex.deal.models.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryAppInfoServiceImpl implements SummaryAppInfoService {

    private final ApplicationService applicationService;
    private final ModelMapper modelMapper;

    public SummaryAppInfoDTO getSummaryAppInfo(Long id) {

        log.info("getSummaryAppInfo() - SummaryAppInfoDTO: Получение Application из базы данных");
        Application application = applicationService.getApplication(id);
        log.info("getSummaryAppInfo() - SummaryAppInfoDTO: Объект Application получен из базы данных");

        log.info("getSummaryAppInfo() - SummaryAppInfoDTO: Формирование paymentScheduleElementList");
        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>();

        for (int i = 0; i < application.getCredit().getPaymentSchedule().size(); i++) {
            paymentScheduleElementList.add(modelMapper.map(application.getCredit().getPaymentSchedule().get(i), PaymentScheduleElement.class));
        }
        log.info("getSummaryAppInfo() - SummaryAppInfoDTO: Список paymentScheduleElementList сформирован");

        log.info("getSummaryAppInfo() - SummaryAppInfoDTO: Создание SummaryAppInfoDTO из полученных данных");
        return SummaryAppInfoDTO.builder()
                .fullName(application.getClient().getLastName() + " " + application.getClient().getFirstName() + " " + application.getClient().getMiddleName())
                .birthdate(application.getClient().getBirthdate())
                .gender(application.getClient().getGender().toString())
                .fullPassportData(application.getClient().getPassport().getNumber() + " " + application.getClient().getPassport().getSeries()
                        + " выдан " + application.getClient().getPassport().getIssueBranch() + " " + application.getClient().getPassport().getIssueDate())
                .email(application.getClient().getEmail())
                .martialStatus(application.getClient().getMaritalStatus().toString())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(modelMapper.map(application.getClient().getEmployment(), EmploymentDTO.class))
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .monthlyPayment(application.getCredit().getMonthlyPayment())
                .rate(application.getCredit().getRate())
                .psk(application.getCredit().getPsk())
                .isInsuranceEnabled(application.getCredit().getAddServices().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getAddServices().getIsSalaryClient())
                .paymentScheduleElementList(paymentScheduleElementList)
                .sesCode(Integer.valueOf(application.getSesCode()))
                .build();
    }
}
