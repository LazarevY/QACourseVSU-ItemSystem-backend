package app.core.data.jpa.repository;

import app.core.data.jpa.persistance.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> getByLogin(String login);
    boolean existsByLogin(String login);
}
