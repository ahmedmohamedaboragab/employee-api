package employee_api.controller;

import employee_api.dto.ApiResponse;
import employee_api.dto.DepartmentRequest;
import employee_api.dto.DepartmentResponse;
import employee_api.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>>
    getAllDepartments() {

        return ResponseEntity.ok(
                service.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>>
    getDepartmentById(@PathVariable Long id) {

        DepartmentResponse department =
                service.getDepartmentById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Department retrieved successfully",
                        department
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>>
    createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse department =
                service.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Department created successfully",
                                department
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse>
    updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                service.updateDepartment(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        service.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}