package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.entity.Organization;
import task.entity.User;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findById(int id);
    boolean existsByName (String name);
    boolean existsByIdAndUser (int id, User user);
    List<Organization> findAllByUser (User user);

    @Modifying
    @Query("update Organization o set o.name = :name  where o.id = :id")
    void updateName (@Param("id") int id, @Param("name") String name);

    @Modifying
    @Query("update Organization o set o.description = :description  where o.id = :id")
    void updateDescription (@Param("id") int id, @Param("description") String description);

    @Modifying
    @Query("update Organization o set o.logo = :logo where o.id = :id")
    void updateLogo (@Param("id") int id, @Param("logo") String logo);

    @Modifying
    @Query("update Organization o set o.balance = (o.balance + :balance) where o.id = :id")
    void gainMoney (@Param("id") int id, @Param("balance") double balance);

    @Modifying
    @Query("update Organization o set o.balance = (o.balance - :balance) where o.id = :id")
    void returnMoney (@Param("id") int id, @Param("balance") double balance);

    @Modifying
    @Query("update Organization o set o.status= :status where o.id = :id")
    void changeStatus (@Param("id") int id, @Param("status") String status);
}
