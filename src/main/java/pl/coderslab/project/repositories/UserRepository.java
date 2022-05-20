package pl.coderslab.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1 ")
    User findUserByEmail(String email);

}
