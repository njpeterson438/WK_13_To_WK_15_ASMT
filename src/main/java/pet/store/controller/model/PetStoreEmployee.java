package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
    private Long employeeId;
    private String employeeName;

    // Constructor to initialize PetStoreEmployee from an Employee entity
    public PetStoreEmployee(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.employeeName = employee.getEmployeeName();
    }
}
