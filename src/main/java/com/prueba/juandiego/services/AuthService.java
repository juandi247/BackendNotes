package com.prueba.juandiego.services;

import com.prueba.juandiego.dtos.AuthResponseDTO;
import com.prueba.juandiego.dtos.LoginRequestDTO;
import com.prueba.juandiego.dtos.RegisterRequestDTO;
import com.prueba.juandiego.models.User;
import com.prueba.juandiego.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class AuthService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;



    /*
     * Handles user login.
     * 1. Authenticates the user with the provided username and password.
     * 2. If authentication is successful, fetches user details.
     * 3. Generates a JWT token for the authenticated user.
     *
     * @param request contains the login credentials (username, password)
     * @return AuthResponseDTO with a JWT token
     */

    public AuthResponseDTO login(LoginRequestDTO request) {

        //Authentication for the user by the usenrame and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        //looking into the db for the name
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();


        String token = jwtService.getToken(user);

        //returning the token from the user
        return AuthResponseDTO.builder()
                .token(token)
                .build();

    }





    /*
     * Registers a new user.
     * 1. Encodes the password before saving the user.
     * 2. Saves the new user to the database.
     * 3. Generates a JWT token for the registered user.
     *
     * @param request contains user registration details (username, password)
     * @return AuthResponseDTO with a JWT token
     */


    public AuthResponseDTO register(RegisterRequestDTO request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //creating a new user
        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .build();

        //saving user
        userRepository.save(user);



        //Generation of a new token for the new user
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}