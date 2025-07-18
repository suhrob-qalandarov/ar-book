package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.Auditable;
import org.example.arbook.model.base.BaseEntity;
import org.example.arbook.model.enums.QrCodeStatus;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qr_codes")
public class QrCode extends BaseEntity {
    private String value;
    private UUID userId;
    private UUID bookId;
    private UUID orderId;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private QrCodeStatus status = QrCodeStatus.CREATED;
}
