package task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.entity.Notification;
import task.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUser (User user);

}
