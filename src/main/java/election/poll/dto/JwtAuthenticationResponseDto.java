package election.poll.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponseDto {

    private String accessToken;
    private String tokenType = "Bearer ";

    public JwtAuthenticationResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
