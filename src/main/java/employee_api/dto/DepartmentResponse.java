package employee_api.dto;

public class DepartmentResponse {

    private Long id;
    private String name;

    public DepartmentResponse() {
    }

    public DepartmentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}