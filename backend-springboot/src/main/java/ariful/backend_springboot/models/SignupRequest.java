package ariful.backend_springboot.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotEmpty
    @NonNull
    private String email;
    @NotEmpty
    @NonNull
    private String password;
}
