package org.example.arbook.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.LoginReq;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.auth.CodeVerificationReq;
import org.example.arbook.model.dto.request.auth.PhoneVerificationReq;
import org.example.arbook.model.dto.response.LoginRes;
import org.example.arbook.model.dto.response.auth.AuthResponse;

public interface AuthService {

    void register(@Valid RegisterReq registerReq);

    void verifyPhoneNumber(String phoneNumber, String code);

    String sendLoginVerificationCode(@Valid PhoneVerificationReq phoneVerificationReq);

    AuthResponse verifyAndLogin(@Valid CodeVerificationReq codeVerificationReq, HttpServletResponse response);

    String logOut(HttpServletResponse response);
}
