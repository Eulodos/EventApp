package pl.aw.iventz.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByStartDateAfter(Date currentDate);

    @Query(value = "SELECT e FROM Event e WHERE e.endDate > CURRENT_TIMESTAMP")
    List<Event> findAllByEndDateAfter(Date currentDate);

    @Query(value = "SELECT e FROM Event e WHERE UPPER(e.title) LIKE CONCAT('%',UPPER(?1),'%')")
    List<Event> findAllByTitleLike(String query);

    @Query(value = "SELECT e FROM Event e WHERE UPPER(e.title) LIKE CONCAT('%',UPPER(?1),'%') AND e.startDate > ?2")
    List<Event> findAllByTitleLikeAndStartDateAfter(String query, Date date);

    @Query(value = "SELECT e FROM Event e WHERE UPPER(e.title) LIKE CONCAT('%',UPPER(?1),'%') AND e.endDate > ?2")
    List<Event> findAllByTitleLikeAndEndDateAfter(String query, Date date);
}
