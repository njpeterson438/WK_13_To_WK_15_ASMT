package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;
    
//    Method to handle GET requests to list all pet stores
    @GetMapping
    public List<PetStoreData> getAllPetStores() {
        log.info("Fetching all pet stores...");
        return petStoreService.retrieveAllPetStores();
    }
    
//    Method to handle GET requests to retrieve pet store by ID
    @GetMapping("/{petStoreId}")
    public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
        log.info("Received request to get pet store with ID: {}", petStoreId);
        return petStoreService.retrievePetStoreById(petStoreId);
    }


    // Method to handle POST requests for creating a new PetStore
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
        log.info("Received request to create pet store: {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }

    // Method to handle PUT requests for updating an existing PetStore
    @PutMapping("/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId,
                                       @RequestBody PetStoreData petStoreData) {
        log.info("Received request to update pet store with ID {}: {}", petStoreId, petStoreData);
        petStoreData.setPetStoreId(petStoreId);  // Set the ID in the DTO
        return petStoreService.savePetStore(petStoreData);
    }

    // Method to handle POST requests for adding an employee to a PetStore
    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petStoreId,
                                                  @RequestBody PetStoreEmployee petStoreEmployee) {
        log.info("Received request to add employee to pet store with ID {}: {}", petStoreId, petStoreEmployee);
        return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
    }

    // Method to handle POST requests for adding a customer to a PetStore
    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreCustomer addCustomerToPetStore(@PathVariable Long petStoreId,
                                                  @RequestBody PetStoreCustomer petStoreCustomer) {
        log.info("Received request to add customer to pet store with ID {}: {}", petStoreId, petStoreCustomer);
        return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
    }
    
// Method to handle DELETE requests for deleting store by ID
    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStore(@PathVariable Long petStoreId) {
        log.info("Received request to delete pet store with ID: {}", petStoreId);
        petStoreService.deletePetStoreById(petStoreId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet store deleted successfully");
        return response;
    }

}
