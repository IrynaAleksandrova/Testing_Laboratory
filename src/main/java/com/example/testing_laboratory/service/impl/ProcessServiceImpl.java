package com.example.testing_laboratory.service.impl;

import com.example.testing_laboratory.dto.AbstractProcessDto;
import com.example.testing_laboratory.dto.CleaningProcessDto;
import com.example.testing_laboratory.dto.DeliveryDto;
import com.example.testing_laboratory.dto.PaymentForWorkProcessDto;
import com.example.testing_laboratory.dto.ProductionProcessDto;
import com.example.testing_laboratory.entity.CleaningProcess;
import com.example.testing_laboratory.entity.Client;
import com.example.testing_laboratory.entity.DeliveryReportProcess;
import com.example.testing_laboratory.entity.PaymentForWorkProcess;
import com.example.testing_laboratory.entity.Process;
import com.example.testing_laboratory.entity.ProductionProcess;
import com.example.testing_laboratory.entity.User;
import com.example.testing_laboratory.exception.ProcessException;
import com.example.testing_laboratory.exception.StartProcessException;
import com.example.testing_laboratory.mapper.ProcessMapper;
import com.example.testing_laboratory.repository.ProcessRepository;
import com.example.testing_laboratory.service.ClientService;
import com.example.testing_laboratory.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;
    private final ClientService clientService;

    @Override
    public Optional<Process> saveProcess(Process process) {
        return Optional.of(processRepository.save(process));
    }

    @Override
    public CleaningProcess startCleaningProcess(User employee, List<Client> clients, CleaningProcessDto cleaningProcessDto) {
        CleaningProcess cleaningProcess = processMapper.convertCleaningProcessDtoToCleaningProcess(cleaningProcessDto);
        setClientListToStartProcess(cleaningProcess, clients);
        cleaningProcess.setEmployee(employee);
        cleaningProcess.setProcessStart(LocalDateTime.now());
        log.info("cleaning process start");
        return cleaningProcess;
    }

    public ProductionProcess startProductionProcess(User employee, List<Client> clients, ProductionProcessDto productionProcessDto) {
        ProductionProcess productionProcess = processMapper.convertProductionProcessDtoToProductionProcess(productionProcessDto);
        setClientListToStartProcess(productionProcess, clients);
        productionProcess.setEmployee(employee);
        productionProcess.setProcessStart(LocalDateTime.now());
        log.info("production process start");
        return productionProcess;
    }

    @Override
    public DeliveryReportProcess startDeliveryReportProcess(User employee, List<Client> clients, DeliveryDto deliveryDto) {
        DeliveryReportProcess deliveryReportProcess = processMapper.convertDeliveryDtoToDeliveryReportProcess(deliveryDto);
        setClientListToStartProcess(deliveryReportProcess, clients);
        deliveryReportProcess.setEmployee(employee);
        deliveryReportProcess.setProcessStart(LocalDateTime.now());
        log.info("delivery report process start");
        return deliveryReportProcess;
    }

    public PaymentForWorkProcess checkPaymentForWorkProcess(User employee, List<Client> clients, PaymentForWorkProcessDto paymentForWorkProcessDto) {
        PaymentForWorkProcess paymentForWorkProcess = processMapper.convertPaymentForWorkProcessDtoToPaymentForWorkProcess(paymentForWorkProcessDto);
        setClientListToStartProcess(paymentForWorkProcess, clients);
        paymentForWorkProcess.setEmployee(employee);
        paymentForWorkProcess.setProcessStart(LocalDateTime.now());
        log.info("checking payment for work");
        return paymentForWorkProcess;
    }

    @Override
    public void stopProcess(String clientQrCode) {
        Process processByClient = findProcessByClient(clientQrCode);
        processByClient.setProcessEnd(LocalDateTime.now());
        saveProcess(processByClient);
        log.info("stop process");
        Optional<Client> clientByQrCode = clientService.findClientByQrCode(clientQrCode);
        Client client = clientByQrCode.get();
        client.setProcess(false);
        if (processByClient instanceof DeliveryReportProcess) {
            client.setLastDeliveryDate(LocalDate.now());
        }
        if (processByClient instanceof PaymentForWorkProcess) {
            client.setLastPaymentDate(LocalDate.now());
        }
        clientService.updateClient(clientByQrCode.get());
    }

    @Override
    public List<AbstractProcessDto> findProcessListByClient(String clientQrCode) {
        List<Process> allProcesses = processRepository.findAll();
        List<AbstractProcessDto> clientProcess = new ArrayList<>();
        for (Process process : allProcesses) {
            List<Client> client = process.getClient();
            for (Client e : client) {
                if (e.getQrCode().equals(clientQrCode)) {
                    AbstractProcessDto abstractProcessDto = processMapper.convertAbstractProcessToAbstractProcessDto(process);
                    clientProcess.add(abstractProcessDto);
                }
            }
        }
        return clientProcess;
    }

    @Override
    public void setClientListToStartProcess(Process process, List<Client> clients) {
        process.setClient(new ArrayList<>());
        for (Client client : clients) {
            List<AbstractProcessDto> processListByClient = findProcessListByClient(client.getQrCode());
            AbstractProcessDto abstractProcessDto = new AbstractProcessDto();
            if (!processListByClient.isEmpty()) {
                abstractProcessDto = processListByClient.get(processListByClient.size() - 1);
            }
            if (processListByClient.isEmpty()
                    || (abstractProcessDto.getProcessEnd() != null && abstractProcessDto.getCleaningEquipment() != null)
                    || process instanceof CleaningProcess) {
                client.setProcess(true);
                Optional<Client> updateClient = clientService.updateClient(client);
                process.getClient().add(updateClient.get());
            } else {
                throw new StartProcessException();
            }
        }
    }

    @Override
    public Process findProcessByClient(String clientQrCode) {
        List<Process> unfinishedProcesses = processRepository.findUnfinishedProcesses();
        for (Process process : unfinishedProcesses) {
            List<Client> client = process.getClient();
            for (Client e : client) {
                if (e.getQrCode().equals(clientQrCode)) {
                    return process;
                }
            }
        }
        throw new ProcessException();
    }
}
