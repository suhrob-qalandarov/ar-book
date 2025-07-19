package org.example.arbook.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.auth.CodeVerificationReq;
import org.example.arbook.model.dto.request.auth.PhoneVerificationReq;
import org.example.arbook.model.dto.response.auth.UserRes;
import org.example.arbook.model.entity.User;

public interface AuthService {

    String register(@Valid RegisterReq registerReq);

    String sendLoginVerificationCode(@Valid PhoneVerificationReq phoneVerificationReq);

    UserRes verifyBothRegisterAndLogin(@Valid CodeVerificationReq codeVerificationReq, HttpServletResponse response);

    String logOut(HttpServletResponse response);

    UserRes getUserData(User user);
}
