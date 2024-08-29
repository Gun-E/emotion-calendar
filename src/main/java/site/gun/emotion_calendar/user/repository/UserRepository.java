package site.gun.emotion_calendar.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gun.emotion_calendar.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmailLike(String userEmail);
}
