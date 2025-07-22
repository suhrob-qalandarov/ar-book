package org.example.arbook.model.dto.response;

import org.example.arbook.model.dto.response.auth.UserRes;

import java.util.List;
import java.util.UUID;

public record LoginRes(
        String token,
        UserRes userRes
){
}
