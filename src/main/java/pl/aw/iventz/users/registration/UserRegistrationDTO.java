package pl.aw.iventz.users.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(exclude = {"password"})
public class UserRegistrationDTO {

    @Email(message = "Podano nieprawidłowy email")
    private String email;

    @Size(min = 8, max = 30, message = "Hasło musi zawierać od {min} do {max} znaków. Podano ${validatedValue.length()}.")
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$", message = "Nazwa użytkownika musi zawierać od 1 do 50 znaków oraz nie może zawierać jedynie białych znaków, podano ${validatedValue.length()}.")
    private String displayName;
}
