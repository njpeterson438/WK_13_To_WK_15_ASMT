package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EmployeeDao employeeDao;

    // Method to save or update PetStore data
    @Transactional
    public PetStoreData savePetStore(PetStoreData petStoreData) {
        PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());
        copyPetStoreFields(petStore, petStoreData);
        PetStore savedPetStore = petStoreDao.save(petStore);
        return new PetStoreData(savedPetStore);
    }

    // Helper method to find or create a PetStore
    private PetStore findOrCreatePetStore(Long petStoreId) {
        if (petStoreId == null) {
            return new PetStore();
        }
        return petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStoreId + " not found"));
    }

    // Helper method to copy fields from DTO to entity
    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setStoreName(petStoreData.getStoreName());

        // Handle updating customers and employees
        if (petStoreData.getCustomers() != null) {
            Set<Customer> newCustomers = new HashSet<>();
            for (PetStoreCustomer customerData : petStoreData.getCustomers()) { // Updated to PetStoreCustomer
                Customer customer = new Customer();
                customer.setCustomerId(customerData.getCustomerId());
                customer.setCustomerEmail(customerData.getCustomerEmail());
                customer.setCustomerName(customerData.getCustomerName());
                newCustomers.add(customer);
            }
            petStore.setCustomers(newCustomers);
        }
        
        if (petStoreData.getEmployees() != null) {
            Set<Employee> newEmployees = new HashSet<>();
            for (PetStoreEmployee employeeData : petStoreData.getEmployees()) { // Updated to PetStoreEmployee
                Employee employee = new Employee();
                employee.setEmployeeId(employeeData.getEmployeeId());
                employee.setEmployeeName(employeeData.getEmployeeName());
                employee.setPetStore(petStore);  // Ensure the employee is linked to the PetStore
                newEmployees.add(employee);
            }
            petStore.setEmployees(newEmployees);
        }
    }

    // Method to save an employee to an existing pet store
    @Transactional
    public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet store not found"));

        // Create a new employee object
        Employee employee = new Employee();
        employee.setEmployeeName(petStoreEmployee.getEmployeeName());
        employee.setPetStore(petStore);  // Link employee to pet store

        // Add employee to pet store's employees set
        petStore.getEmployees().add(employee);
        employeeDao.save(employee);  // Save the employee

        // Return the employee as a PetStoreEmployee DTO
        return new PetStoreEmployee(employee);
    }

    // Method to save a customer to an existing pet store
    @Transactional
    public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
        // Find the pet store by ID
        PetStore petStore = petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet store not found"));

        // Create a new customer object
        Customer customer = new Customer();
        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
        customer.setCustomerName(petStoreCustomer.getCustomerName());

        // Add the customer to the pet store's customer list
        petStore.getCustomers().add(customer);

        // Save the customer entity
        customerDao.save(customer);

        // Return the customer as a PetStoreCustomer DTO
        return new PetStoreCustomer(customer);
    }

//    Method to list all pet stores
    @Transactional
    public List<PetStoreData> retrieveAllPetStores() {
        // Fetch all PetStore entities from the database
        List<PetStore> petStores = petStoreDao.findAll();

        // Convert PetStore entities to PetStoreData DTOs
        List<PetStoreData> petStoreDataList = petStores.stream()
            .map(PetStoreData::new)  // Assuming a constructor in PetStoreData that takes a PetStore
            .collect(Collectors.toList());

        // Remove customers and employees to only return summary data
        petStoreDataList.forEach(petStoreData -> {
            petStoreData.setCustomers(null);  // Remove customers
            petStoreData.setEmployees(null);  // Remove employees
        });

        return petStoreDataList;
    }

// Method to retrieve pet store by ID
    @Transactional
    public PetStoreData retrievePetStoreById(Long petStoreId) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStoreId + " not found"));
        return new PetStoreData(petStore);
    }
    
// Method to delete pet store by ID
    @Transactional
    public void deletePetStoreById(Long petStoreId) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStoreId + " not found"));

        petStoreDao.delete(petStore); // This deletes the pet store and its associated data
    }


}
