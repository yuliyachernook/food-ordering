package by.ita.chernook.repository;

import by.ita.chernook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u WHERE u.login = :login")
    Optional<User> findByLogin(String login);
}
