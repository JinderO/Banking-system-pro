package com.jindero.banking.features.user;

import com.jindero.banking.features.user.dto.AuthResponse;
import com.jindero.banking.features.user.dto.RegisterRequest;
import com.jindero.banking.features.user.dto.LoginRequest;
import com.jindero.banking.shared.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //Konstruktor
    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    //Metoda register
    public AuthResponse register(RegisterRequest request){
       String hashedPassword = passwordEncoder.encode(request.password());
       User user = new User(request.firstName(), request.lastName(),
               hashedPassword, request.email(), request.phoneNumber(),
               null ,null);

        userService.createUser(user);

        return AuthResponse.of(jwtService.generateToken(request.email()));
    }

    //Metoda login
    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        return AuthResponse.of(jwtService.generateToken(request.email()));
    }

}
