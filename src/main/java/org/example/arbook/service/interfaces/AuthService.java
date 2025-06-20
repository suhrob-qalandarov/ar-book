package org.example.arbook.service.interfaces;

import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.LoginReq;
import org.example.arbook.model.dto.request.RegisterReq;

public interface AuthService {
    String logIn(LoginReq loginReq);

    void register(@Valid RegisterReq registerReq);

    void verifyPhoneNumber(String phoneNumber, String code);
}
