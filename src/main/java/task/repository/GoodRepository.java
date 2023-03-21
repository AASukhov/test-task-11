package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.entity.Discount;
import task.entity.Good;
import task.entity.Organization;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Good, Integer> {
    Good findGoodById (int id);
    boolean existsById(int id);
    List<Good> findAllByOrganization (Organization organization);

    List<Good> findAllByDiscount(Discount discount);

    @Modifying
    @Query("update Good g set g.description = :description  where g.id = :id")
    void descriptionChange (@Param("id") int id, @Param("description") String description);

    @Modifying
    @Query("update Good g set g.keywords = :keywords  where g.id = :id")
    void keywordsChange (@Param("id") int id, @Param("keywords") String keywords);

    @Modifying
    @Query("update Good g set g.price = :price  where g.id = :id")
    void priceChange (@Param("id") int id, @Param("price") Double price);

    @Modifying
    @Query("update Good g set g.features = :features  where g.id = :id")
    void featuresChange (@Param("id") int id, @Param("features") String features);

    @Modifying
    @Query("update Good g set g.amount = (g.amount - :amount)  where g.id = :id")
    void goodBought (@Param("id") int id, @Param("amount") int amount);

    @Modifying
    @Query("update Good g set g.amount = (g.amount + :amount)  where g.id = :id")
    void goodReturn (@Param("id") int id, @Param("amount") int amount);

    @Modifying
    @Query("update Good g set g.estimation = :estimation where g.id = :id")
    void changeEstimation (@Param("id") int id, @Param("estimation") double estimation);
}
