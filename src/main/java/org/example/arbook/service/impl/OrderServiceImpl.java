package org.example.arbook.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.*;
import org.example.arbook.model.dto.response.order.*;
import org.example.arbook.model.entity.*;
import org.example.arbook.model.enums.OrderStatus;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.OrderRepository;
import org.example.arbook.repository.QrCodeRepository;
import org.example.arbook.repository.UserRepository;
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
    public OrderRes getOrderRes(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return convertToOrderRes(order);
    }

    @Override
    public AcceptedOrderRes getAcceptedOrderRes(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return convertToAcceptedOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes getOrderResByName(String orderName) {
        Order order = orderRepository.findByName(orderName);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public AcceptedOrderRes acceptOrderAndGetQrCodes(UUID orderId) {
        Order order = orderRepository.acceptAndReturn(orderId);
        List<AcceptedOrderItemRes> acceptedOrderItemRes = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {
            List<String> qrCodes = createQrCode(item.getBook().getId(), item.getAmount(), order.getId());
            AcceptedOrderItemRes.builder()
                    .amount(item.getAmount())
                    .adminBookRes(bookMapper.toAdminBookResponse(item.getBook()))
                    .qrCodes(qrCodes)
                    .build();
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
    public OrderRes declineOrder(UUID orderId) {
        Order order = orderRepository.declineAndReturn(orderId);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes pendOrder(UUID orderId) {
        Order order = orderRepository.pendAndReturn(orderId);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes createOrder(OrderReq orderReq) {
        // Validate input
        if (orderReq.userId() == null || orderReq.name() == null || orderReq.orderItemReqList() == null) {
            throw new IllegalArgumentException("User ID, name, and order items are required");
        }

        Order order = convertToNewOrder(orderReq);
        Order saved = orderRepository.save(order);
        return convertToOrderRes(saved);
    }

    @Transactional
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

    private Order convertToNewOrder(OrderReq orderReq) {
        // Validate user existence
        User user = userRepository.findById(orderReq.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + orderReq.userId()));

        // Create Order
        Order order = Order.builder()
                .name(orderReq.name())
                .userId(orderReq.userId())
                .status(OrderStatus.PENDING)
                .isActive(true) // Set BaseEntity field
                .createdBy("system") // Adjust based on authentication
                .updatedBy("system") // Adjust based on authentication
                .orderItems(new ArrayList<>()) // Initialize empty list
                .build();

        // Map OrderItemReq to OrderItem
        List<OrderItem> orderItems = (List<OrderItem>) orderReq.orderItemReqList().stream()
                .map(orderItemReq -> {
                    // Validate book existence
                    Book book = bookRepository.findById(orderItemReq.bookId())
                            .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + orderItemReq.bookId()));
                    return OrderItem.builder()
                            .amount(orderItemReq.amount())
                            .book(book)
                            .order(order) // Set the Order reference
                            .isActive(true) // Set BaseEntity field
                            .createdBy("system")
                            .updatedBy("system")
                            .build();
                })
                .toList();

        // Set orderItems on Order
        order.setOrderItems(orderItems);

        return order;
    }

    private Order convertToOrder(OrderReq orderReq) {
        return Order.builder()
                .name(orderReq.name())
                .userId(orderReq.userId())
                .status(OrderStatus.PENDING)
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

    private AcceptedOrderRes convertToAcceptedOrderRes(Order order) {
        User user = userRepository.findById(order.getUserId()).orElseThrow();
        List<QrCode> qrCodes = qrCodeRepository.findByOrderId(order.getId());
        return AcceptedOrderRes.builder()
                .id(order.getId())
                .name(order.getName())
                .status(order.getStatus().name())
                .userRes(UserRes.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .orderItemList(order.getOrderItems().stream().map(orderItem -> AcceptedOrderItemRes.builder()
                        .amount(orderItem.getAmount())
                        .adminBookRes(adminBookService.getOneBook(orderItem.getBook().getId()))
                        .qrCodes(qrCodes.stream().map(qrCode -> qrCode.getId().toString()).toList())
                        .build()).toList())
                .build();
    }

    /// Not used
    private String shortUuid(UUID uuid) {
        byte[] uuidBytes = toBytes(uuid);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(uuidBytes);
    }

    private byte[] toBytes(UUID uuid) {
        byte[] bytes = new byte[16];
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();

        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (mostSigBits >>> (8 * (7 - i)));
            bytes[8 + i] = (byte) (leastSigBits >>> (8 * (7 - i)));
        }
        return bytes;
    }
}
