package uz.polito.das.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.polito.das.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}

