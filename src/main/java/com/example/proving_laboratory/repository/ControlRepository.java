package com.example.proving_laboratory.repository;

import com.example.proving_laboratory.entity.AbstractControl;
import com.example.proving_laboratory.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ControlRepository extends JpaRepository<AbstractControl, UUID> {

    @Query(value = "select * from control_object where o_type = 'EQ' and  qr_code =:qrCode", nativeQuery = true)
    Optional<Client> findClientByQrCode(String qrCode);

    @Query(value = "select * from control_object where o_type = 'EQ'", nativeQuery = true)
    List<Client> findAllClient();

}
