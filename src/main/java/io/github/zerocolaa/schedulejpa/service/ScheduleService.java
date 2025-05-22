package io.github.zerocolaa.schedulejpa.service;

import io.github.zerocolaa.schedulejpa.dto.schedule.*;
import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.entity.Schedule;
import io.github.zerocolaa.schedulejpa.repository.AuthorRepository;
import io.github.zerocolaa.schedulejpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository   authorRepository;

    //일정 생성
    public ScheduleResponseDto createSchedule(Long authorId, CreateScheduleRequestDto requestDto) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Schedule schedule = requestDto.toEntity(author);
        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(saved);
    }

    //일정 전체 조회
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::new)
                .toList();

    }
    //id별로 일정 단건 조회
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ScheduleResponseDto(schedule);
    }

    //작성자 별 일정 전체 조회
    public List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId) {
        List<Schedule> schedules = scheduleRepository.findAllByAuthor_Id(authorId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    //수정
    public ScheduleResponseDto updateSchedule(Long authorId, Long scheduleId,
                                      UpdateScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getAuthor().getId().equals(authorId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        schedule.updateSchedule(dto.getTitle(), dto.getContents());

        return new ScheduleResponseDto(schedule);
    }

    //삭제
    public void deleteSchedule(Long authorId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getAuthor().getId().equals(authorId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        scheduleRepository.delete(schedule);
    }


}
