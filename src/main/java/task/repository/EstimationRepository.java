package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.entity.Estimation;
import task.entity.Good;
import task.entity.User;

import java.util.List;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation,Integer> {
    List<Estimation> findAllByGood(Good good);
    Estimation findByGoodAndUser (Good good, User user);

    @Modifying
    @Query("update Estimation e set e.review = :review where e.id = :id")
    void changeEstimation (@Param("id") int id, @Param("review") String review);
}
