package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.BaseEntity;
import org.example.arbook.model.enums.BookStatus;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    private String title;
    private String description;
    private Integer totalPages;
    private Short totalLanguages;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BookStatus status=BookStatus.CREATED;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Attachment attachment;


}
