package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.entity.Good;
import task.entity.Story;
import task.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    boolean existsByGoodAndUser(Good good, User user);
    List<Story> findAllByUser(User user);

    Story findByGoodAndUser (Good good, User user);
}
