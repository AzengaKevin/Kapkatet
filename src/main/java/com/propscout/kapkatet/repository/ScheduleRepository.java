package com.propscout.kapkatet.repository;

import com.propscout.kapkatet.model.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleItem, Long> {

    List<ScheduleItem> findByCenterId(@Param("center_id") long centerId);

    List<ScheduleItem> findByUserId(@Param("user_id") long userId);
}
