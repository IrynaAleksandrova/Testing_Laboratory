package com.example.proving_laboratory.repository;

import com.example.proving_laboratory.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByQrCode(String qrCode);

}
