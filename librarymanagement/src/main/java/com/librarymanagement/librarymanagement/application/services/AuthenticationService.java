package com.librarymanagement.librarymanagement.application.services;


import com.librarymanagement.librarymanagement.api.dto.AuthenticationRequest;
import com.librarymanagement.librarymanagement.api.dto.AuthenticationResponse;
import com.librarymanagement.librarymanagement.api.dto.RegisterRequest;

import com.librarymanagement.librarymanagement.application.common.interfaces.IRoleRepository;
import com.librarymanagement.librarymanagement.application.common.interfaces.IUserRepository;
import com.librarymanagement.librarymanagement.application.dto.authUserResponse;
import com.librarymanagement.librarymanagement.domain.AppUser;
import com.librarymanagement.librarymanagement.domain.Role;
import com.librarymanagement.librarymanagement.infrastrcture.Security.JwtService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public Either<String,authUserResponse> register(RegisterRequest request)
    {

       if(userRepository.findByEmail(request.email).isPresent())
       {
           return Either.left("Email already exists");
       }


      var roleOpt = roleRepository.findByName("User");
      Role role;
      List<Role> roles = List.of();

      if(roleOpt.isPresent())
      {
         role = roleOpt.get().get(0);
         roles = List.of(role);
      }



      var user = AppUser.builder()
              .firstName(request.getFirstName())
              .lastName(request.getLastName())
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword()))
              .roles(roles)
              .build();

      userRepository.save(user);

      var jwtToken = jwtService.generateToken(user);

        return Either.right(authUserResponse.builder()
                .token(jwtToken)
                .user(user)
                .build()
        );

    }

    public Either<String,authUserResponse> authenticate (AuthenticationRequest request)
    {

        if(userRepository.findByEmail(request.getEmail()).isEmpty())
        {
            return Either.left("Email not found");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return Either.right(authUserResponse.builder()
                .token(jwtToken)
                .user(user)
                .build()
        );
    }

}
