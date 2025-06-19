package org.example.arbook.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    private String text;

    @OneToOne
    private Attachment audio;

    @ManyToOne
    private Language language;

    @ManyToOne
    private BookPage bookPage;
}
