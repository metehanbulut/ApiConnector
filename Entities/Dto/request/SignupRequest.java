package com.linklegel.apiContact.Entities.Dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignupRequest {
  @NotNull(message = "Kullanıcı Adı Gereklidir.!")
  @Length(min = 8,max = 20,message = "Kullanıcı Adı Minimum 8 Maksimum 20 Karakter Olmalıdır!")
  private String username;

  @NotNull(message = "Mail Adresi Zorunludur !")
  @Size(max=50,message = "Güvenlik Sebebi ile Mail Adresi Maksimum 50 Karakter Olmalıdır")
  @Email(message = "Mail Adresi Hatalı !. Tekrar Kontrol Edin.")
  private String email;


  @NotNull(message = "Parola Gereklidir.!")
  @Length(min = 8,max = 100,message = "Parola Minimum 8 Maksimum 100 Karakter Olmalıdır!")
  private String password;

}
