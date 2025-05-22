package io.github.zerocolaa.schedulejpa.repository;

import io.github.zerocolaa.schedulejpa.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByAuthor_Id(Long authorId);
}
