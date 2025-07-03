package org.example.arbook.service.interfaces;

import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.entity.QrCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<QrCode> createOrderAndGetQrCodes(OrderReq orderReq);
}
