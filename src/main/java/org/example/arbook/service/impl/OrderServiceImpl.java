package org.example.arbook.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.model.dto.request.OrderItemReq;
import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.entity.Order;
import org.example.arbook.model.entity.QrCode;
import org.example.arbook.model.enums.OrderStatus;
import org.example.arbook.model.enums.QrCodeStatus;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.OrderRepository;
import org.example.arbook.repository.QrCodeRepository;
import org.example.arbook.service.interfaces.OrderService;
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

        savedQrCodes.forEach(qrCode -> qrCode.setValue(shortUuid(qrCode.getId())));

        return qrCodeRepository.saveAll(savedQrCodes);    }

    private Order convertToOrder(OrderReq orderReq) {
        return Order.builder()
                .name(orderReq.name())
                .userId(orderReq.userId())
                .status(OrderStatus.PENDING)
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
