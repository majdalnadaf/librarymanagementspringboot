package com.librarymanagement.librarymanagement.api;


import com.librarymanagement.librarymanagement.api.dto.AuthenticationRequest;
import com.librarymanagement.librarymanagement.api.dto.AuthenticationResponse;
import com.librarymanagement.librarymanagement.api.dto.RegisterRequest;
import com.librarymanagement.librarymanagement.application.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController extends BaseController {


    private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse> Register(@Valid @RequestBody RegisterRequest request)
  {
     var authUserResponseOpt = authenticationService.register(request);
     if(authUserResponseOpt.isLeft())
     {
       Map<String, String> errors = new HashMap<>();
       errors.put("error", authUserResponseOpt.getLeft());
       return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(),errors,null));
     }


    return ResponseEntity
            .ok()
            .body(new ApiResponse<>(HttpStatus.OK.toString(),null,authUserResponseOpt.get()));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> Login(@Valid @RequestBody AuthenticationRequest request)
  {
     var authUserResponseOpt = authenticationService.authenticate(request);

     if(authUserResponseOpt.isLeft())
     {
       Map<String, String> errors = new HashMap<>();
       errors.put("error", authUserResponseOpt.getLeft());
       return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(),errors,null));
     }

    return ResponseEntity
            .ok()
            .body(new ApiResponse<>(HttpStatus.OK.toString(),null,authUserResponseOpt.get()));
  }


}
