package io.github.zerocolaa.schedulejpa.dto.schedule;


import io.github.zerocolaa.schedulejpa.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePagedResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String authorName;
    private final int commentCount;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime modifiedDateTime;


    private SchedulePagedResponseDto(Long id, String title, String contents, String authorName,
                                     int commentCount, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.authorName = authorName;
        this.commentCount = commentCount;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
    public static SchedulePagedResponseDto from(Schedule schedule, int commentCount) {
        return new SchedulePagedResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getAuthor().getUserName(),
                commentCount,
                schedule.getCreatedDateTime(),
                schedule.getModifiedDateTime()
        );
    }

}
