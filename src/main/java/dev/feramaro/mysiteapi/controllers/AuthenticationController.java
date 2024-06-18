package dev.feramaro.mysiteapi.controllers;

import dev.feramaro.mysiteapi.dto.common.ResponseDTO;
import dev.feramaro.mysiteapi.dto.user.LoginDTO;
import dev.feramaro.mysiteapi.dto.user.RegisterDTO;
import dev.feramaro.mysiteapi.dto.user.TokenDTO;
import dev.feramaro.mysiteapi.models.User;
import dev.feramaro.mysiteapi.services.AuthenticationService;
import dev.feramaro.mysiteapi.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO) {
        authenticationService.signUp(registerDTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        User authUser = authenticationService.authenticate(loginDTO);
        String token = jwtService.generateToken(authUser);
        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .httpOnly(true)
                .secure(false)
                .maxAge(24 * 60 * 60)
                .path("/")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ResponseDTO(200, "Login successfully!"));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logOut() {
        ResponseCookie cookie = ResponseCookie.from("Authorization", "")
                .httpOnly(true)
                .secure(false)
                .maxAge(-1000)
                .path("/")
                .build();

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
