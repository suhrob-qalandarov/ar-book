package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.BaseEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "page_contents")
public class PageContent extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToOne
    private Attachment audio;

    @ManyToOne
    private Language language;

    @ManyToOne
    private BookPage bookPage;
}
