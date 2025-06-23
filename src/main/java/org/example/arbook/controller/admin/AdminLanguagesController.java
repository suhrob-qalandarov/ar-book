package org.example.arbook.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.entity.Language;
import org.example.arbook.repository.LanguageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;
import static org.example.arbook.util.ExceptionCodes.CODE_200;

@Tag(name = "Admin Languages Controller", description = "API for managing languages in the admin panel")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + LANGUAGES)
public class AdminLanguagesController {

    private final LanguageRepository languageRepository;

    @Operation(summary = "Get all languages list", responses = {
            @ApiResponse(responseCode = CODE_200, description = "Languages list retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Language>> getCategories(){
        List<Language> languages = languageRepository.findAll();
        return ResponseEntity.ok(languages);
    }
}
