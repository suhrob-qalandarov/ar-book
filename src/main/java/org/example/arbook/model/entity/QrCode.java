package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.Auditable;
import org.example.arbook.model.enums.QrCodeStatus;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qr_codes")
public class QrCode extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String value;
    private Long userId;
    private Long bookId;
    private Long orderId;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private QrCodeStatus status = QrCodeStatus.CREATED;

    @Builder.Default
    private Boolean isActive = true;

}
