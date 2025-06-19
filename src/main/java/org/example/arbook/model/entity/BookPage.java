package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_pages")
public class BookPage extends BaseEntity {

    @OneToOne
    private Attachment markerPhoto;

    @OneToOne
    private Attachment file3D;

    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "bookPage")
    private List<PageContent> pageContents;
}
