package io.github.zerocolaa.schedulejpa.dto.schedule;

import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    //author_id는 세션에서 받으므로 dto에는 제목 내용만 두면 됨

    @NotBlank
    @Size(max=30,message = "제목은 30자 이내로 작성해주세요.")
    private final String title;

    @NotEmpty
    @Size(max=1000,message = "내용을 1000자 이내로 작성해주세요.")
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
