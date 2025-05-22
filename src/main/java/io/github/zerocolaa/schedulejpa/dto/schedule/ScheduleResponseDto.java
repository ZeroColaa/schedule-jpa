package io.github.zerocolaa.schedulejpa.dto.schedule;

import io.github.zerocolaa.schedulejpa.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private final Long   id;
    private final String title;
    private final String contents;
    private final String userName;


    public ScheduleResponseDto(Schedule schedule) {
        this.id         = schedule.getId();
        this.title      = schedule.getTitle();
        this.contents   = schedule.getContents();
        this.userName = schedule.getAuthor().getUserName();
    }

}
