package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Guest;
import mk.finki.db.hotel_management.model.User;
import mk.finki.db.hotel_management.model.dto.LoginDto;
import mk.finki.db.hotel_management.model.dto.RegisterDto;

public interface UserService {
    User findByUsername(String username);
    User register(RegisterDto registerDto);
    User login(LoginDto loginDto);
    boolean isAdmin(User user);
    Guest findGuestByUsername(String username);
}
