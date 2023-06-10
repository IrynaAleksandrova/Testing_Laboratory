package com.example.testing_laboratory.service.impl;

import com.example.testing_laboratory.entity.ObjectControl;
import com.example.testing_laboratory.entity.Client;
import com.example.testing_laboratory.entity.Department;
import com.example.testing_laboratory.exception.ClientNotFoundException;
import com.example.testing_laboratory.exception.SaveException;
import com.example.testing_laboratory.repository.ControlObjectRepository;
import com.example.testing_laboratory.service.ClientService;
import com.example.testing_laboratory.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ControlObjectRepository controlRepository;
    private final DepartmentService departmentService;

    @Override
    public Optional<Client> saveClient(Client client) {
        if (!controlRepository.existsById(client.getId())) {
            Client saveClient = controlRepository.save(client);
            departmentService.updateDepartmentWithClient(saveClient.getDepartment().getId(), saveClient);
            log.info("save client " + saveClient.getNameClient());
            return Optional.of(saveClient);
        } else {
            throw new SaveException();
        }
    }

    @Override
    public Optional<Client> updateClient(Client client) {
        if (controlRepository.existsById(client.getId())) {
            Client updateClient = controlRepository.save(client);
            log.info("update client " + updateClient.getNameClient());
            return Optional.of(updateClient);
        } else {
            throw new ClientNotFoundException();
        }
    }

    @Override
    public List<Client> findAllClient() {
        return controlRepository.findAllClient();
    }

    @Override
    public Optional<Client> findClientByQrCode(String clientQrCode) {
        return controlRepository.findClientByQrCode(clientQrCode);
    }

    @Override
    public List<Client> findClientOfDepartment(UUID departmentId) {
        Optional<Department> departmentById = departmentService.findDepartmentById(departmentId);
        return departmentById.get().getClientList();
    }

    @Override
    public List<Client> findListOfInternalCodes(List<String> clientQrCodeList) {
        return clientQrCodeList.stream().map(s -> controlRepository.findClientByQrCode(s).get()).toList();
    }

    @Override
    public List<Client> findFreeClientOfDepartment(UUID departmentId) {
        List<Client> clientOfDepartment = findClientOfDepartment(departmentId);
        return clientOfDepartment.stream().filter(client -> !client.isProcess()).toList();
    }

    @Override
    public void deleteClient(UUID id) {
        Optional<ObjectControl> clientById = controlRepository.findById(id);
        controlRepository.deleteById(id);
        Department department = clientById.get().getDepartment();
        department.getClientList().remove(clientById.get());
        departmentService.updateDepartment(department);
    }
}
