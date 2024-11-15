package pet.store.controller.model;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
    private Long petStoreId;
    private String storeName;
    private Set<PetStoreCustomer> customers;
    private Set<PetStoreEmployee> employees;

    // Constructor that takes a PetStore entity as a parameter
    public PetStoreData(PetStore petStore) {
        this.petStoreId = petStore.getPetStoreId();
        this.storeName = petStore.getStoreName();
        this.customers = petStore.getCustomers().stream()
            .map(PetStoreCustomer::new)
            .collect(Collectors.toSet());
        this.employees = petStore.getEmployees().stream()
            .map(PetStoreEmployee::new)
            .collect(Collectors.toSet());
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreCustomer {
        private Long customerId;
        private String customerEmail;
        private String customerName;

        // Constructor that takes a Customer entity as a parameter
        public PetStoreCustomer(Customer customer) {
            this.customerId = customer.getCustomerId();
            this.customerEmail = customer.getCustomerEmail();
            this.customerName = customer.getCustomerName();
        }
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreEmployee {
        private Long employeeId;
        private String employeeName;

        // Constructor that takes an Employee entity as a parameter
        public PetStoreEmployee(Employee employee) {
            this.employeeId = employee.getEmployeeId();
            this.employeeName = employee.getEmployeeName();
        }
    }
}
