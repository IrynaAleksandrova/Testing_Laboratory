package com.example.testing_laboratory.repository;

import com.example.testing_laboratory.entity.AbstractObjectControl;
import com.example.testing_laboratory.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ControlObjectRepository extends JpaRepository<AbstractObjectControl, UUID> {

    @Query(value = "select * from control_object where o_type = 'CQ' and  qr_code =:qrCode", nativeQuery = true)
    Optional<Client> findClientByQrCode(String qrCode);

    @Query(value = "select * from control_object where o_type = 'CQ'", nativeQuery = true)
    List<Client> findAllClient();

}
