package pl.aw.iventz.user_enrolled_event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.aw.iventz.users.registration.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEnrolledEventRepository extends JpaRepository<UserEnrolledEvent, Long> {

    @Query(value = "SELECT u FROM UserEnrolledEvent u WHERE u.event.id=?1 AND u.user.id=?2")
    Optional<UserEnrolledEvent> findByEventAndUser(Long eventId, Long userId);

    //todo: znaleźć i wtedy zamienić
//    @Query(value = "DELETE FROM UserEnrolledEvent u WHERE u.event.id=?1 AND u.user.id=?2")
//    void deleteByEventAndUser(Long eventId, Long userId);

    @Query(value = "SELECT u FROM UserEnrolledEvent uee JOIN User u ON uee.user.id=u.id")
    List<User> findByEventId(Long eventId);

}
