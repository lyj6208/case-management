package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.CommonObjectResponse;
import com.testing_company.case_management.dto.LoginRequestDTO;
import com.testing_company.case_management.dto.TokenResponseDTO;
import com.testing_company.case_management.model.RefreshToken;
import com.testing_company.case_management.service.RefreshTokenService;
import com.testing_company.case_management.service.UserService;
import com.testing_company.case_management.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "認證 API", description = "使用者認證相關 API")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @Operation(summary = "使用者登入", description = "使用者登入並取得 JWT token")
    public ResponseEntity<CommonObjectResponse<TokenResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        log.info("Login attempt for user：{}",loginRequest.getUsername());
        try {
            Authentication authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();

            String accessToken= jwtUtil.generateAccessToken(userDetails);
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(userDetails.getUsername());
            TokenResponseDTO response = TokenResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .tokenType("Bearer")
                    .expiresIn(900L) // 15 分鐘
                    .build();

            return ResponseEntity.ok(CommonObjectResponse.success(response, "登入成功"));
        }catch (BadCredentialsException e) {
            log.warn("Login failed for user {}: Invalid credentials", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CommonObjectResponse.error("使用者名稱或密碼錯誤"));
        } catch (DisabledException e) {
            log.warn("Login failed for user {}: Account disabled", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(CommonObjectResponse.error("帳號已被停用"));
        } catch (Exception e) {
            log.error("Login failed for user {}: {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonObjectResponse.error("登入失敗，請稍後再試"));
        }
    }

}
