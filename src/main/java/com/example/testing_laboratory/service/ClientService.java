package com.example.testing_laboratory.service;

import com.example.testing_laboratory.entity.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {

    Optional<Client> saveClient(Client client);

    Optional<Client> updateClient(Client client);

    List<Client> findAllClient();

    Optional<Client> findClientByQrCode(String clientQrCode);

    List<Client> findClientOfDepartment(UUID departmentId);

    List<Client> findListOfInternalCodes(List<String> clientQrCodeList);

    List<Client> findFreeClientOfDepartment(UUID departmentId);

    void deleteClient(UUID id);
}
