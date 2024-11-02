package com.librarymanagement.librarymanagement.application.dto;


import com.librarymanagement.librarymanagement.domain.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class authUserResponse {

   public AppUser user;
   public String token;

}
