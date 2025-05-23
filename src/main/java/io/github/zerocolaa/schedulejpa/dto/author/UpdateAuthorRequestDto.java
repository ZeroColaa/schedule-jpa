package io.github.zerocolaa.schedulejpa.dto.author;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateAuthorRequestDto {

    @NotBlank
    @Size(max=10,message="유저명은 최대 10자까지 입력해주세요.")
    private final String userName;


    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    public UpdateAuthorRequestDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
