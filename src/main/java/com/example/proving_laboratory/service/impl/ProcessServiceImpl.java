package com.example.proving_laboratory.service.impl;

import com.example.proving_laboratory.dto.AbstractProcessDto;
import com.example.proving_laboratory.dto.CleaningProcessDto;
import com.example.proving_laboratory.dto.ExtraditionDto;
import com.example.proving_laboratory.dto.PaymentForWorkProcessDto;
import com.example.proving_laboratory.dto.ProductionProcessDto;
import com.example.proving_laboratory.entity.AbstractProcess;
import com.example.proving_laboratory.entity.CleaningProcess;
import com.example.proving_laboratory.entity.Client;
import com.example.proving_laboratory.entity.ExtraditionProcess;
import com.example.proving_laboratory.entity.PaymentForWorkProcess;
import com.example.proving_laboratory.entity.ProductionProcess;
import com.example.proving_laboratory.entity.User;
import com.example.proving_laboratory.exception.ProcessException;
import com.example.proving_laboratory.exception.StartProcessException;
import com.example.proving_laboratory.mapper.ProcessMapper;
import com.example.proving_laboratory.repository.ProcessRepository;
import com.example.proving_laboratory.service.ClientService;
import com.example.proving_laboratory.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;
    private final ClientService clientService;

    @Override
    public Optional<AbstractProcess> saveProcess(AbstractProcess process) {
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
    public ExtraditionProcess startExtraditionProcess(User employee, List<Client> clients, ExtraditionDto extraditionDto) {
        ExtraditionProcess extraditionProcess = processMapper.convertExtraditionDtoToExtraditionProcess(extraditionDto);
        setClientListToStartProcess(extraditionProcess, clients);
        extraditionProcess.setEmployee(employee);
        extraditionProcess.setProcessStart(LocalDateTime.now());
        log.info("extradition process start");
        return extraditionProcess;
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
        AbstractProcess processByClient = findProcessByClient(clientQrCode);
        processByClient.setProcessEnd(LocalDateTime.now());
        saveProcess(processByClient);
        log.info("stop process");
        Optional<Client> clientByQrCode = clientService.findClientByQrCode(clientQrCode);
        Client client= clientByQrCode.get();
        client.setProcess(false);
        if (processByClient instanceof ExtraditionProcess) {
            client.setCompletionDate(LocalDate.now());
        }
        if (processByClient instanceof PaymentForWorkProcess) {
            client.setDeliveryDate(LocalDate.now());
        }
        clientService.updateClient(clientByQrCode.get());
    }

    @Override
    public List<AbstractProcessDto> findProcessListByClient(String clientQrCode) {
        List<AbstractProcess> allProcesses = processRepository.findAll();
        List<AbstractProcessDto> clientProcess = new ArrayList<>();
        for (AbstractProcess process : allProcesses) {
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
    public void setClientListToStartProcess(AbstractProcess process, List<Client> clients) {
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
    public AbstractProcess findProcessByClient(String clientQrCode) {
        List<AbstractProcess> unfinishedProcesses = processRepository.findUnfinishedProcesses();
        for (AbstractProcess process : unfinishedProcesses) {
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
