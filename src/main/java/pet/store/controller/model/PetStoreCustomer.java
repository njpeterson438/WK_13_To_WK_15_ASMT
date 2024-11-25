package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
    private Long customerId;
    private String customerEmail;
    private String customerName;

    // Constructor to initialize PetStoreCustomer from a Customer entity
    public PetStoreCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerEmail = customer.getCustomerEmail();
        this.customerName = customer.getCustomerName();
    }
}
