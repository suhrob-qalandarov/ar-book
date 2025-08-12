package org.example.arbook.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.*;
import org.example.arbook.model.dto.response.order.*;
import org.example.arbook.model.entity.*;
import org.example.arbook.model.enums.OrderStatus;
import org.example.arbook.model.enums.QrCodeStatus;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.repository.*;
import org.example.arbook.service.interfaces.AttachmentService;
import org.example.arbook.service.interfaces.OrderService;
import org.example.arbook.service.interfaces.admin.AdminBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final QrCodeRepository qrCodeRepository;
    private final UserRepository userRepository;
    private final AdminBookService adminBookService;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final OrderItemRepository orderItemRepository;
    private final AttachmentService attachmentService;

    @Transactional
    @Override
    public AdminOrderDashboardRes getOrders() {
        List<OrderRes> pendingOrders = orderRepository.findAllByStatus(OrderStatus.PENDING).stream()
                .map(this::convertToOrderRes)
                .toList();
        List<AcceptedOrderRes> acceptedOrders = orderRepository.findAllByStatus(OrderStatus.ACCEPTED).stream()
                .map(this::convertToAcceptedOrderRes)
                .toList();

        List<OrderRes> declinedOrders = orderRepository.findAllByStatus(OrderStatus.DECLINED).stream()
                .map(this::convertToOrderRes)
                .toList();

        return AdminOrderDashboardRes.builder()
                .pendingCount((long) pendingOrders.size())
                .acceptedCount((long) acceptedOrders.size())
                .declinedCount((long) declinedOrders.size())
                .pendingOrders(pendingOrders)
                .acceptedOrders(acceptedOrders)
                .declinedOrders(declinedOrders)
                .build();
    }

    @Transactional
    @Override
    public AcceptedOrderRes getAcceptedOrderRes(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return convertToAcceptedOrderRes(order);
    }

    @Override
    public AcceptedOrderRes acceptOrderAndGetQrCodes(UUID orderId) {
        Order order = orderRepository.acceptAndReturn(orderId);

        if (order.getStatus().equals(OrderStatus.ACCEPTED)) {
            throw new RuntimeException("Cannot accepted, order: " + orderId + " already accepted");
        }

        List<AcceptedOrderItemRes> acceptedOrderItemRes = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {
            List<String> qrCodes = createQrCode(item.getBook().getId(), item.getAmount(), order.getId());
            acceptedOrderItemRes.add(AcceptedOrderItemRes.builder()
                    .amount(item.getAmount())
                    .adminBookRes(bookMapper.toAdminBookResponse(item.getBook()))
                    .qrCodes(qrCodes)
                    .build()
            );
        }
        return AcceptedOrderRes.builder()
                .id(order.getId())
                .name(order.getName())
                .status(order.getStatus().name())
                .orderItemList(acceptedOrderItemRes)
                .build();
    }

    @Transactional
    @Override
    public AcceptedOrderRes setOrUpdateImage(UUID orderId, UUID imageId) {
        Order updated = orderRepository.setOrUpdateBackgroundImage(orderId, imageId);
        if (updated == null) {
            throw new RuntimeException("Order not found or not ACCEPTED: " + orderId);
        }
        return convertToAcceptedOrderRes(updated);
    }

    @Transactional
    @Override
    public OrderRes declineOrder(UUID orderId) {
        if (orderRepository.isAccepted(orderId)) throw new RuntimeException("Cannot decline an ACCEPTED order");
        Order order = orderRepository.declineAndReturn(orderId);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes pendOrder(UUID orderId) {
        if (orderRepository.isAccepted(orderId)) throw new RuntimeException("Cannot pend an ACCEPTED order");
        if (orderRepository.isDeclined(orderId)) throw new RuntimeException("Cannot pend an DECLINED order");

        Order order = orderRepository.pendAndReturn(orderId);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public AcceptedOrderRes blockOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("It's not active accepted order, ID: " + orderId);
        }
        qrCodeRepository.findByOrderId(orderId).forEach(qrCode -> qrCode.setStatus(QrCodeStatus.BLOCKED));
        order.setStatus(OrderStatus.BLOCKED);
        return convertToAcceptedOrderRes(order);
    }

    @Transactional
    @Override
    public AcceptedOrderRes unblockOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        if (order.getStatus() != OrderStatus.BLOCKED) {
            throw new IllegalStateException("It's not blocked order, ID: " + orderId);
        }
        qrCodeRepository.findByOrderId(orderId).forEach(qrCode -> qrCode.setStatus(QrCodeStatus.ACTIVE));
        order.setStatus(OrderStatus.ACCEPTED);
        return convertToAcceptedOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes createOrder(OrderReq orderReq) {
        if (orderReq.userId() == null || orderReq.name() == null || orderReq.orderItemReqList() == null) {
            throw new IllegalArgumentException("User ID, name, and order items are required");
        }
        Order savedOrder = convertToOrderAnsSaveReturn(orderReq);
        return convertToOrderRes(savedOrder);
    }

    public List<String> createQrCode(UUID bookId, int amount, UUID orderId) {
        List<QrCode> qrCodes = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            qrCodes.add(QrCode.builder()
                    .bookId(bookId)
                    .orderId(orderId)
                    .build());
        }
        List<QrCode> savedQrCodes = qrCodeRepository.saveAll(qrCodes);
        return savedQrCodes.stream().map(qrCode -> qrCode.getId().toString()).toList();
    }

    @Transactional
    public Order convertToOrderAnsSaveReturn(OrderReq orderReq) {
        userRepository.findById(orderReq.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + orderReq.userId()));

        Order order = convertToOrder(orderReq);
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = (List<OrderItem>) orderReq.orderItemReqList().stream()
                .map(orderItemReq -> {
                    Book book = bookRepository.findById(orderItemReq.bookId())
                            .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + orderItemReq.bookId()));
                    return OrderItem.builder()
                            .amount(orderItemReq.amount())
                            .book(book)
                            .order(order)
                            .build();
                }).toList();
        savedOrder.setOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems);
        return order;
    }

    @Transactional
    public Order convertToOrder(OrderReq orderReq) {
        return Order.builder()
                .name(orderReq.name())
                .userId(orderReq.userId())
                .status(OrderStatus.PENDING)
                .orderItems(new ArrayList<>())
                .build();
    }

    @Transactional
    public OrderRes convertToOrderRes(Order order) {
        User user = userRepository.findById(order.getUserId()).orElseThrow();
        return OrderRes.builder()
                .id(order.getId())
                .name(order.getName())
                .status(order.getStatus().name())
                .userRes(UserRes.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .orderItemResList(order.getOrderItems().stream()
                        .map(orderItem -> OrderItemRes.builder()
                                .amount(orderItem.getAmount())
                                .adminBookRes(adminBookService.getOneBook(orderItem.getBook().getId()))
                                .build()
                        ).toList()
                )
                .build();
    }

    @Transactional
    public AcceptedOrderRes convertToAcceptedOrderRes(Order order) {
        User user = userRepository.findById(order.getUserId()).orElseThrow();
        return AcceptedOrderRes.builder()
                .id(order.getId())
                .name(order.getName())
                .status(order.getStatus().name())
                .attachmentId(order.getBackgroundImage() != null ? order.getBackgroundImage().getId() : null)
                .userRes(UserRes.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .orderItemList(order.getOrderItems().stream()
                        .map(orderItem -> AcceptedOrderItemRes.builder()
                                .amount(orderItem.getAmount())
                                .adminBookRes(adminBookService.getOneBook(orderItem.getBook().getId()))
                                .qrCodes(qrCodeRepository.findByOrderIdAndBookId(order.getId(), orderItem.getBook().getId()).stream()
                                        .map(qrCode -> qrCode.getId().toString())
                                        .toList())
                        .build())
                        .toList()
                )
                .build();
    }
}
