package org.example.arbook.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.arbook.model.base.BaseEntity;
import org.example.arbook.model.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private String name;

    @OneToOne
    private Attachment backgroundImage;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private UUID userId;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
