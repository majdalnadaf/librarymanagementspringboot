package com.librarymanagement.librarymanagement.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotNull
  public String firstName;
  @NotNull

  public String lastName;
  @NotNull
  @Email
  public String email;
  @Length( min = 8 ,max = 70 )
  public String password;

}
