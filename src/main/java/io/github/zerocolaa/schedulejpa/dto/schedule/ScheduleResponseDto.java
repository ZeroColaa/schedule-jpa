package io.github.zerocolaa.schedulejpa.dto.schedule;

import io.github.zerocolaa.schedulejpa.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private final Long   id;
    private final String title;
    private final String contents;
    private final String userName;


    private ScheduleResponseDto(Long id, String title, String contents, String userName) {
        this.id       = id;
        this.title    = title;
        this.contents = contents;
        this.userName = userName;
    }

    public static ScheduleResponseDto from(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getAuthor().getUserName()
        );
    }

}
