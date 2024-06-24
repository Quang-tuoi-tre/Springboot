package honhatquang890.gmail.com.lab2.service;

import honhatquang890.gmail.com.lab2.model.Role;
import honhatquang890.gmail.com.lab2.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final IRoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}