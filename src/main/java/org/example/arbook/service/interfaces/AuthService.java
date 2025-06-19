package org.example.arbook.service.interfaces;

import org.example.arbook.model.dto.request.LogInDTO;
import org.example.arbook.model.dto.response.LogInResDTO;

public interface AuthService {
    LogInResDTO logIn(LogInDTO logInDTO);
}
