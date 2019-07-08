package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(long[] ids) {
        if (ids == null) return;

        for (long id : ids) {
            userRepository.deleteById(id);
        }
    }

    @Transactional
    public boolean addUser(String login, String passHash, String salt,
                           Collection<Role> roles, String email,
                           String phone) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, salt, roles, email, phone);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void updateUser(String login, String email, String phone) {
        CustomUser user = userRepository.findByLogin(login);

        user.setEmail(email);
        user.setPhone(phone);

        userRepository.save(user);
    }
}
