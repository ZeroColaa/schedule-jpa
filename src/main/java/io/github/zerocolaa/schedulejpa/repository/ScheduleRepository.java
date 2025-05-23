package io.github.zerocolaa.schedulejpa.repository;

import io.github.zerocolaa.schedulejpa.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByAuthor_Id(Long authorId);

    default Schedule findByIdOrElseThrow(Long scheduleId) {
        return findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
    }
}
