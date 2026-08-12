package employee_api.service;

import employee_api.dto.EmployeeRequest;
import employee_api.dto.EmployeeResponse;
import employee_api.entity.Employee;
import employee_api.exception.DepartmentNotFoundException;
import employee_api.exception.EmployeeNotFoundException;
import employee_api.repository.EmployeeRepository;
import employee_api.repository.DepartmentRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import employee_api.entity.Department;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository repository, DepartmentRepository departmentRepository) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
    }

    public List<EmployeeResponse> getAllEmployees() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        return toResponse(employee);
    }

    public List<EmployeeResponse> searchEmployees(
            String keyword) {

        return repository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmployeeResponse> getEmployeesByDepartment(
            Long departmentId) {

        return repository
                .findByDepartmentId(departmentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Department department =
                departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + request.getDepartmentId()
                                )
                        );

        Employee employee = new Employee();

        return getEmployeeResponse(request, department, employee);
    }

    @NonNull
    private EmployeeResponse getEmployeeResponse(EmployeeRequest request, Department department, Employee employee) {
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setDepartment(department);

        Employee savedEmployee = repository.save(employee);

        return toResponse(savedEmployee);
    }

    @NonNull
    private EmployeeResponse getEmployeeResponse(EmployeeRequest request, Employee employee) {
        Department department =
                departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found"
                                )
                        );
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);
        employee.setSalary(request.getSalary());

        Employee savedEmployee = repository.save(employee);

        return toResponse(savedEmployee);
    }

    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        Department department =
                departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + request.getDepartmentId()
                                )
                        );

        return getEmployeeResponse(request, department, employee);
    }

    public void deleteEmployee(Long id) {

        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(
                    "Employee not found with id: " + id
            );
        }

        repository.deleteById(id);
    }

    private EmployeeResponse toResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getSalary()
        );
    }
}