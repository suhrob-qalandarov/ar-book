package org.example.arbook.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.model.dto.request.OrderItemReq;
import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.*;
import org.example.arbook.model.entity.Order;
import org.example.arbook.model.entity.QrCode;
import org.example.arbook.model.entity.User;
import org.example.arbook.model.enums.OrderStatus;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.OrderRepository;
import org.example.arbook.repository.QrCodeRepository;
import org.example.arbook.repository.UserRepository;
import org.example.arbook.service.interfaces.OrderService;
import org.example.arbook.service.interfaces.admin.AdminBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final QrCodeRepository qrCodeRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AdminBookService adminBookService;

    @Transactional
    @Override
    public AdminOrderDashboardRes getOrders() {
        List<OrderRes> pendingOrders = orderRepository.findAllByStatus(OrderStatus.PENDING).stream()
                .map(this::convertToOrderRes)
                .toList();
        List<OrderRes> acceptedOrders = orderRepository.findAllByStatus(OrderStatus.ACCEPTED).stream()
                .map(this::convertToOrderRes)
                .toList();

        return AdminOrderDashboardRes.builder()
                .pendingCount((long) pendingOrders.size())
                .acceptedCount((long) acceptedOrders.size())
                .pendingOrders(pendingOrders)
                .acceptedOrders(acceptedOrders)
                .build();
    }

    @Transactional
    @Override
    public OrderRes getOrderRes(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes getOrderResByName(String orderName) {
        Order order = orderRepository.findByName(orderName);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes acceptOrder(Long orderId) {
        Order order = orderRepository.acceptAndReturn(orderId);
        return convertToOrderRes(order);
    }

    @Transactional
    @Override
    public OrderRes declineOrder(Long orderId) {
        Order order = orderRepository.declineAndReturn(orderId);
        return convertToOrderRes(order);
    }

    @Transactional
    public List<QrCode> createOrderAndGetQrCodes(OrderReq orderReq) {
        Order order = convertToOrder(orderReq);
        order = orderRepository.save(order);

        List<QrCode> allQrCodes = new ArrayList<>();

        for (OrderItemReq item : orderReq.orderItemReqList()) {
            if (!bookRepository.existsBookByIdIs(item.bookId())) {
                throw new BookNotFoundException("Book not found with id: " + item.bookId());
            }
            List<QrCode> qrCodes = createQrCode(item.bookId(), item.amount(), order.getId());
            allQrCodes.addAll(qrCodes);
        }
        return allQrCodes;
    }

    @Transactional
    public List<QrCode> createQrCode(Long bookId, int amount, long orderId) {
        List<QrCode> qrCodes = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            qrCodes.add(QrCode.builder()
                    .bookId(bookId)
                    .orderId(orderId)
                    .build());
        }
        List<QrCode> savedQrCodes = qrCodeRepository.saveAll(qrCodes);

        //savedQrCodes.forEach(qrCode -> qrCode.setValue(shortUuid(qrCode.getId())));

        return qrCodeRepository.saveAll(savedQrCodes);
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
