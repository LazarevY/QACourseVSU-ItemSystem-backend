package app.core.data.jpa.repository;

import app.core.data.jpa.persistance.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> findByPhone(String phone);
    boolean existsByPhone(String phone);
}
