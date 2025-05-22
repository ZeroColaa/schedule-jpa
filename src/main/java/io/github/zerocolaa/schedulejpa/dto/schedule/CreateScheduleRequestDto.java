package io.github.zerocolaa.schedulejpa.dto.schedule;

import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.entity.Schedule;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    //author_id는 세션에서 받으므로 dto에는 제목 내용만 두면 됨
    private final String title;
    private final String contents;

    public CreateScheduleRequestDto(String title, String contents) {
        this.title    = title;
        this.contents = contents;
    }

    public Schedule toEntity(Author author){
        Schedule schedule = new Schedule(title, contents);
        schedule.setAuthor(author);
        return schedule;
    }
}
