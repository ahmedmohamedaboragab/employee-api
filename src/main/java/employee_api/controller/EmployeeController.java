package employee_api.controller;

import employee_api.dto.ApiResponse;
import employee_api.dto.EmployeeRequest;
import employee_api.dto.EmployeeResponse;
import employee_api.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page cannot be negative"
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Size must be between 1 and 100"
            );
        }

        return ResponseEntity.ok(
                service.getAllEmployees(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchEmployees(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                service.searchEmployees(keyword)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    getEmployeeById(@PathVariable Long id) {

        EmployeeResponse employee =
                service.getEmployeeById(id);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee retrieved successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>>
    getEmployeesByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                service.getEmployeesByDepartment(departmentId)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse employee =
                service.createEmployee(request);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee created successfully",
                        employee
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                service.updateEmployee(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        service.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}