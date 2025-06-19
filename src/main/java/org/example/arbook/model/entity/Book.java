package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Integer totalPages;
    private Short totalLanguages;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Attachment attachment;


}
