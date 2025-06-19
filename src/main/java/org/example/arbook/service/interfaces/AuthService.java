package org.example.arbook.service.interfaces;

import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.LogInDTO;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.response.LogInResDTO;

public interface AuthService {
    LogInResDTO logIn(LogInDTO logInDTO);

    void register(@Valid RegisterReq registerReq);

    void verifyPhoneNumber(String phoneNumber, String code);
}
