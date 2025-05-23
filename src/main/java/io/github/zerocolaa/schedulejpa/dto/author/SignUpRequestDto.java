package io.github.zerocolaa.schedulejpa.dto.author;

import io.github.zerocolaa.schedulejpa.entity.Author;
import lombok.Getter;
import jakarta.validation.constraints.*;

@Getter
public class SignUpRequestDto {


    @NotBlank
    @Size(max=10,message="유저명은 최대 10자까지 입력해주세요.")
    private final String userName;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank
    @Size(min=4,message="비밀번호는 최소 4자 이상 입력해주세요.")
    private final String password;

    public SignUpRequestDto(String userName, String email, String password) {
        this.userName = userName;
        this.email      = email;
        this.password   = password;
    }

}
