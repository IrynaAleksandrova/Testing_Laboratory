package com.example.proving_laboratory.service;

import com.example.proving_laboratory.dto.AbstractProcessDto;
import com.example.proving_laboratory.dto.CleaningProcessDto;
import com.example.proving_laboratory.dto.DeliveryDto;
import com.example.proving_laboratory.dto.PaymentForWorkProcessDto;
import com.example.proving_laboratory.dto.ProductionProcessDto;
import com.example.proving_laboratory.entity.AbstractProcess;
import com.example.proving_laboratory.entity.CleaningProcess;
import com.example.proving_laboratory.entity.Client;
import com.example.proving_laboratory.entity.DeliveryReportProcess;
import com.example.proving_laboratory.entity.PaymentForWorkProcess;
import com.example.proving_laboratory.entity.ProductionProcess;
import com.example.proving_laboratory.entity.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProcessService {

    Optional<AbstractProcess> saveProcess(AbstractProcess process);

    CleaningProcess startCleaningProcess(User employee, List<Client> clients, CleaningProcessDto cleaningProcessDto);

    ProductionProcess startProductionProcess(User employee, List<Client> clients, @Valid ProductionProcessDto productionProcessDto);

    DeliveryReportProcess startDeliveryReportProcess(User employee, List<Client> clients, @Valid DeliveryDto deliveryDto);

    PaymentForWorkProcess checkPaymentForWorkProcess(User employee, List<Client> clients, PaymentForWorkProcessDto paymentForWorkProcessDto);

    void stopProcess(String clientQrCode);

    List<AbstractProcessDto> findProcessListByClient(String clientQrCode);

    void setClientListToStartProcess(AbstractProcess process, List<Client> clients);

    AbstractProcess findProcessByClient(String clientQrCode);

}
