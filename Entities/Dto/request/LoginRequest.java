package com.linklegel.apiContact.Entities.Dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotNull(message = "Kullanıcı Adı Gereklidir.!")
	@Length(min = 8,max = 20,message = "Kullanıcı Adı Minimum 8 Maksimum 20 Karakter Olmalıdır!")
  	private String username;
	@NotNull(message = "Parola Gereklidir.!")
	@Length(min = 8,max = 100,message = "Parola Minimum 8 Maksimum 100 Karakter Olmalıdır!")
	private String password;

}
