package mk.finki.db.hotel_management.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String nationality;
    private LocalDate dateOfBirth;
}
