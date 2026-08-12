package employee_api.service;

import employee_api.dto.DepartmentRequest;
import employee_api.dto.DepartmentResponse;
import employee_api.entity.Department;
import employee_api.exception.DepartmentNotFoundException;
import employee_api.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<DepartmentResponse> getAllDepartments() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id) {

        Department department = repository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        return toResponse(department);
    }

    public DepartmentResponse createDepartment(
            DepartmentRequest request) {

        Department department = new Department();

        department.setName(request.getName());

        Department savedDepartment =
                repository.save(department);

        return toResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest request) {

        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: " + id
                                )
                        );

        department.setName(request.getName());

        Department updatedDepartment =
                repository.save(department);

        return toResponse(updatedDepartment);
    }

    public void deleteDepartment(Long id) {

        if (!repository.existsById(id)) {
            throw new DepartmentNotFoundException(
                    "Department not found with id: " + id
            );
        }

        repository.deleteById(id);
    }

    private DepartmentResponse toResponse(
            Department department) {

        return new DepartmentResponse(
                department.getId(),
                department.getName()
        );
    }
}