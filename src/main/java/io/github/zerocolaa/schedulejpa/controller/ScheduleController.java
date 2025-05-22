package io.github.zerocolaa.schedulejpa.controller;

import io.github.zerocolaa.schedulejpa.dto.schedule.*;
import io.github.zerocolaa.schedulejpa.repository.ScheduleRepository;
import io.github.zerocolaa.schedulejpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    //일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody CreateScheduleRequestDto requestDto,
            @SessionAttribute("LOGIN_USER_ID") Long loginUserId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scheduleService.createSchedule(loginUserId, requestDto));
    }

    //scheduleId로 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findScheduleById(scheduleId));

    }

    //로그인한 작성자 일정 전체 조회
    @GetMapping("me")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByAuthor(
            @SessionAttribute("LOGIN_USER_ID") Long loginUserId){
        return ResponseEntity.ok(scheduleService.findSchedulesByAuthor(loginUserId));
    }

    //전체 일정 조회
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAllSchedules());
    }

    //일정 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequestDto requestDto,
            @SessionAttribute("LOGIN_USER_ID") Long loginUserId) {
        return ResponseEntity.ok(scheduleService.updateSchedule(loginUserId, scheduleId, requestDto));
    }

    //일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute("LOGIN_USER_ID") Long loginUserId) {
        scheduleService.deleteSchedule(loginUserId, scheduleId);
        return ResponseEntity.noContent().build();
    }
}
