package org.example.arbook.repository;

import org.example.arbook.model.entity.QrCode;
import org.example.arbook.model.enums.QrCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    List<QrCode> findAllByIsActiveTrueAndStatusAndUserId(QrCodeStatus qrCodeStatus, UUID id);

    List<QrCode> findByOrderId(UUID orderId);

    List<QrCode> findByOrderIdAndBookId(UUID orderId, UUID bookId);
    List<QrCode> findAllByUserIdAndIsActiveTrueAndStatus(UUID userId, QrCodeStatus status);
}