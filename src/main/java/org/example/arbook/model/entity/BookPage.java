package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "book_pages")
public class BookPage extends BaseEntity {

    @OneToOne
    private Attachment markerPhoto;

    @OneToOne
    private Attachment file3D;

    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "bookPage"  ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PageContent> pageContents;
}
