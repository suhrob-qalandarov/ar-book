package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.BaseEntity;
import org.example.arbook.model.enums.BookStatus;

import java.util.List;

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
    private Integer totalLanguages;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.INCOMPLETE;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Attachment attachment;
    @OneToMany(mappedBy = "book")
    private List<BookPage> bookPages;


}
