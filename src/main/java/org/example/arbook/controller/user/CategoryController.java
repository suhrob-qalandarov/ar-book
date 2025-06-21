package org.example.arbook.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.CategoryRes;
import org.example.arbook.service.interfaces.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;
import static org.example.arbook.util.CategoryMessageConstants.*;
import static org.example.arbook.util.ExceptionCodes.*;

/**
 * Controller for handling category endpoints.
 */
@Tag(name = USER_CATEGORY, description = USER_CATEGORY_DESCRIPTION)
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = GET_CATEGORIES_SUMMARY, responses = {
            @ApiResponse(responseCode = CODE_200, description = CATEGORY_RETRIEVED)
    })
    @GetMapping()
    public ResponseEntity<List<CategoryRes>> getCategories(){
        List<CategoryRes> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }
}
