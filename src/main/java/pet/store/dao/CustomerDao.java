package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
    // Spring Data JPA will automatically provide the implementation for common CRUD operations
}
