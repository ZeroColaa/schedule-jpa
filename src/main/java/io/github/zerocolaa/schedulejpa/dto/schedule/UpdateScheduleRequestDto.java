package io.github.zerocolaa.schedulejpa.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    @NotBlank
    @Size(max=30,message = "제목은 30자 이내로 작성해주세요.")
    private final String title;

    @NotEmpty
    @Size(max=1000,message = "내용을 1000자 이내로 작성해주세요.")
    private final String contents;

    public UpdateScheduleRequestDto(String title, String contents) {
        this.title    = title;
        this.contents = contents;
    }
}
