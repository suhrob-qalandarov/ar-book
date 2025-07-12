package org.example.arbook.repository;

import org.example.arbook.model.entity.QrCode;
import org.example.arbook.model.enums.QrCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    List<QrCode> findAllByIsActiveTrueAndStatusAndUserId(QrCodeStatus qrCodeStatus, Long id);

    List<QrCode> findByOrderId(Long orderId);
}