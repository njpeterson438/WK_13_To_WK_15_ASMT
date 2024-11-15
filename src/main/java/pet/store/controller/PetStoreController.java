package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;

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
}
