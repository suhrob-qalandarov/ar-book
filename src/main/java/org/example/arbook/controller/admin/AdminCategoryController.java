package org.example.arbook.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.CategoryReq;
import org.example.arbook.model.dto.request.CategoryUpdateReq;
import org.example.arbook.model.entity.Category;
import org.example.arbook.service.interfaces.admin.AdminCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;
import static org.example.arbook.util.ExceptionCodes.*;
import static org.example.arbook.util.CategoryMessageConstants.*;

/**
 * Controller for managing category operations for administrators.
 */
@Tag(name = ADMIN_CATEGORY, description = ADMIN_CATEGORY_DESCRIPTION)
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + CATEGORY)
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @Operation(summary = GET_CATEGORIES_SUMMARY, responses = {
            @ApiResponse(responseCode = CODE_200, description = CATEGORIES_RETRIEVED)
    })
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = adminCategoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = GET_CATEGORY_SUMMARY, responses = {
            @ApiResponse(responseCode = CODE_200, description = CATEGORY_RETRIEVED),
            @ApiResponse(responseCode = CODE_404, description = CATEGORY_NOT_FOUND)
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId){
        Category category = adminCategoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = CREATE_CATEGORY_SUMMARY, responses = {
            @ApiResponse(responseCode = CODE_200, description = CATEGORY_ADDED),
            @ApiResponse(responseCode = CODE_400, description = CATEGORY_EXIST)
    })
    @PostMapping
    public ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryReq categoryReq) {
       Category category= adminCategoryService.addCategory(categoryReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = UPDATE_CATEGORY_SUMMARY, responses = {
            @ApiResponse(responseCode = CODE_200, description = CATEGORY_UPDATED),
            @ApiResponse(responseCode = CODE_404, description = CATEGORY_NOT_FOUND),
            @ApiResponse(responseCode = CODE_400, description = CATEGORY_EXIST)
    })
    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateReq categoryUpdateReq) {
        adminCategoryService.updateCategory(categoryId, categoryUpdateReq);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> activateOrDeactivateCategory(@PathVariable Long categoryId) {
        String message = adminCategoryService.activateOrDeactivateCategory(categoryId);
        return ResponseEntity.ok("Category " + message);
    }

}
