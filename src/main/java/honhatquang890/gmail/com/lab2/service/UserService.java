package honhatquang890.gmail.com.lab2.service;

import honhatquang890.gmail.com.lab2.model.User;
import honhatquang890.gmail.com.lab2.repository.IRoleRepository;
import honhatquang890.gmail.com.lab2.repository.IUserRepository;
import jakarta.validation.constraints.NotNull;
import honhatquang890.gmail.com.lab2.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.
    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
    public void setDefaultRole(String username) {
        userRepository.findByUsername(username).ifPresentOrElse(user -> {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role not found: USER"));
            user.getRoles().add(userRole);
            userRepository.save(user);
        }, () -> {
            throw new UsernameNotFoundException("User not found");
        });
    }
    // Tải thông tin chi tiết người dùng để xác thực.
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }
    // Tìm kiếm người dùng dựa trên tên đăng nhập.

    // Tìm kiếm tất cả người dùng.

    public void updateRole(User user){
        Optional<User> existingUser = findById(user.getId());
        if(existingUser.isPresent()){
            User dbUser = existingUser.get();
            user.setPassword(dbUser.getPassword());
            userRepository.save(user);
        }
        else{
            userRepository.save(user);
        }
    }
    // Cập nhật vai trò của người dùng.

    public List<User> findAll() {
        return userRepository.findAll();}
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        Set<Role> roles = roleIds.stream().map(roleRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }

    /*public void updateRole(Long userId, String roleName) {
        Optional<User> existingUser = findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            Role newRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

            // Clear existing roles and add the new role
            user.getRoles().clear();
            user.getRoles().add(newRole);

            // Save the updated user to persist changes
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
*/


}

