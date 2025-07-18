package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.Auditable;
import org.example.arbook.model.base.BaseEntity;
import org.example.arbook.model.enums.ContentType;

import java.util.UUID;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class Attachment extends BaseEntity {

    private String fileUrl;
    private String key;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;
}
