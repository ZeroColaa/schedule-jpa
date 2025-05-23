package io.github.zerocolaa.schedulejpa.service;

import io.github.zerocolaa.schedulejpa.dto.schedule.*;
import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.entity.Schedule;
import io.github.zerocolaa.schedulejpa.repository.AuthorRepository;
import io.github.zerocolaa.schedulejpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository   authorRepository;

    //일정 생성
    public ScheduleResponseDto createSchedule(Long authorId, CreateScheduleRequestDto requestDto) {

        Author author = authorRepository.findByIdOrElseThrow(authorId);

        Schedule schedule = requestDto.toEntity(author);
        Schedule saved = scheduleRepository.save(schedule);

        return ScheduleResponseDto.from(saved);
    }

    //일정 전체 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::from)
                .toList();

    }
    //스케쥴 id로 일정 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        return ScheduleResponseDto.from(schedule);
    }

    //작성자 별 일정 전체 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId) {
        List<Schedule> schedules = scheduleRepository.findAllByAuthor_Id(authorId);
        return schedules.stream()
                .map(ScheduleResponseDto::from)
                .toList();
    }

    //일정 수정
    public ScheduleResponseDto updateSchedule(Long authorId, Long scheduleId,
                                      UpdateScheduleRequestDto dto) {

        Schedule schedule = findMySchedule(scheduleId, authorId);
        schedule.updateSchedule(dto.getTitle(), dto.getContents());

        return ScheduleResponseDto.from(schedule);
    }

    //일정 삭제
    public void deleteSchedule(Long authorId, Long scheduleId) {
        Schedule schedule = findMySchedule(scheduleId, authorId);
        scheduleRepository.delete(schedule);
    }


    //로그인 한 사용자의 일정을 찾는 함수
    private Schedule findMySchedule(Long scheduleId, Long authorId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        if (!schedule.getAuthor().getId().equals(authorId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "일정 수정/삭제 권한이 없습니다.");
        }

        return schedule;
    }

}
