package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.PetStore;
import java.util.Set;
import java.util.stream.Collectors;


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
}
