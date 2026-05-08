package mk.finki.db.hotel_management.service.impl;

import mk.finki.db.hotel_management.model.*;
import mk.finki.db.hotel_management.model.dto.LoginDto;
import mk.finki.db.hotel_management.model.dto.RegisterDto;
import mk.finki.db.hotel_management.repository.*;
import mk.finki.db.hotel_management.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GuestRepository guestRepository;
    private final AdminRepository adminRepository;

    public UserServiceImpl(UserRepository userRepository,
                           GuestRepository guestRepository,
                           AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Корисникот не постои"));
    }

    @Override
    @Transactional
    public User register(RegisterDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Корисничкото име веќе постои");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        userRepository.save(user);

        Guest guest = new Guest();
        guest.setFirstName(dto.getFirstName());
        guest.setLastName(dto.getLastName());
        guest.setEmail(dto.getEmail());
        guest.setNationality(dto.getNationality());
        guest.setDateOfBirth(dto.getDateOfBirth());
        guest.setUser(user);
        guestRepository.save(guest);

        return user;
    }

    @Override
    public User login(LoginDto dto) {
        return userRepository.findByUsername(dto.getUsername())
                .filter(u -> u.getPassword().equals(dto.getPassword()))
                .orElseThrow(() -> new RuntimeException("Погрешно корисничко име или лозинка"));
    }

    @Override
    public boolean isAdmin(User user) {
        return adminRepository.existsById(user.getUserId());
    }

    @Override
    public Guest findGuestByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Корисникот не постои"));

        return guestRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Гостинот не постои"));
    }
}