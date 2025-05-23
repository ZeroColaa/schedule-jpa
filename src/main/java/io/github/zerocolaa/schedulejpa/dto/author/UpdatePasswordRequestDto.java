package io.github.zerocolaa.schedulejpa.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @NotBlank
    private final String currentPassword;

    @NotBlank
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    private final String newPassword;

    public UpdatePasswordRequestDto(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
