package com.botdiscord.discord.service;

import com.botdiscord.discord.dto.AuthRequest;
import com.botdiscord.discord.dto.AuthResponse;
import com.botdiscord.discord.dto.RegisterRequest;
import com.botdiscord.discord.entity.User;
import com.botdiscord.discord.enums.Role;
import com.botdiscord.discord.repository.UserRepository;
import com.botdiscord.discord.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String token = jwtUtils.generateToken(
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String token = jwtUtils.generateToken(
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
        );
        return new AuthResponse(token);
    }
}

