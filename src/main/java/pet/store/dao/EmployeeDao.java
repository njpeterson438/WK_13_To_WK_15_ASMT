package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
    // Spring Data JPA will automatically provide the implementation for common CRUD operations
}
