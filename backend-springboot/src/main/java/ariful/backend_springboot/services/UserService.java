package ariful.backend_springboot.services;

import ariful.backend_springboot.config.JwtUtils;
import ariful.backend_springboot.models.*;
import ariful.backend_springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> signUp(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    public AuthResponse signIn(SigninRequest signinRequest) {

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signinRequest.getEmail(),
                        signinRequest.getPassword()
                ));
        User user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(String.valueOf(user));

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public ResponseEntity<?> changePassword(PasswordChangeRequest request, Principal principal) {

        //Get current logged in user
        String username = principal.getName();
        User currentUser = this.userRepository.findUserByEmail(username);

        //Check if old password matches
        if (this.passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            currentUser.setPassword(request.getNewPassword());
        }else {
            return new ResponseEntity<>("Existing password do not match", HttpStatus.BAD_REQUEST);
        }

        //Save new password
        userRepository.save(currentUser);

        //Create new JTW token for the user with new password
        String jwtToken = jwtUtil.generateToken(String.valueOf(currentUser));

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtToken)
                .build());
    }
}
