package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public boolean createRoleIfNotFound(String name) {
        if (!roleRepository.existsByName(name)) {
            Role role = new Role(name);
            roleRepository.save(role);

            return true;
        }
        return false;
    }
}
