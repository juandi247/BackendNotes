package com.prueba.juandiego.authcontrollers;


import com.prueba.juandiego.dtos.AuthResponseDTO;
import com.prueba.juandiego.dtos.LoginRequestDTO;
import com.prueba.juandiego.dtos.RegisterRequestDTO;
import com.prueba.juandiego.repositories.UserRepository;
import com.prueba.juandiego.services.AuthService;
import com.prueba.juandiego.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;




    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request){


        return ResponseEntity.ok(authService.login(request));
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request){
        return ResponseEntity.ok(authService.register(request));
    }



    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Verifica que el token sea pasado en el formato correcto
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);  // Token no proporcionado correctamente
        }

        // Extrae el token del encabezado (sin el prefijo "Bearer ")
        String token = authorizationHeader.substring(7);  // Elimina el prefijo "Bearer "

        // Extraer el nombre de usuario del token
        String username = jwtService.getUsernameFromToken(token);

        // Buscar el usuario en la base de datos
        UserDetails user = userRepository.findByUsername(username).orElse(null);

        if (user != null && jwtService.isTokenValid(token, user)) {
            return ResponseEntity.ok(true);  // El token es válido
        } else {
            return ResponseEntity.ok(false); // El token no es válido
        }
    }

    }



