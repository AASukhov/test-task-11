package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByName(String name);

    User findUserById (int id);
    boolean existsByName(String name);

    @Modifying
    @Query("update User u set u.balance = (u.balance + :balance)  where u.id = :id")
    void addBalance (@Param("id") int id, @Param("balance") double balance);

    @Modifying
    @Query("update User u set u.balance = (u.balance - :balance)  where u.id = :id")
    void payForGood (@Param("id") int id, @Param("balance") double balance);

    @Modifying
    @Query("update User u set u.balance = (u.balance + :balance)  where u.id = :id")
    void returnForGood (@Param("id") int id, @Param("balance") double balance);
}
