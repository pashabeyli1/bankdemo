package az.orient.bankdemo.controller;

import az.orient.bankdemo.dto.request.AuthRequest;
import az.orient.bankdemo.dto.response.AuthResponse;
import az.orient.bankdemo.jwt.JwtTokenUtil;
import az.orient.bankdemo.security.MyUserDetails;
import az.orient.bankdemo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;

    private final JwtTokenUtil jwtUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            LOGGER.info("/auth/login request: "+request);
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()));

            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);

            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);
            LOGGER.info("/auth/login response: "+response);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            LOGGER.error("/auth/login error: ",ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
