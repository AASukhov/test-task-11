package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.entity.Discount;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Discount findById(int id);
    boolean existsById(int id);
}
