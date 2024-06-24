package honhatquang890.gmail.com.lab2.controller;

import honhatquang890.gmail.com.lab2.model.User;
import honhatquang890.gmail.com.lab2.model.Role;
import honhatquang890.gmail.com.lab2.repository.IRoleRepository;
import honhatquang890.gmail.com.lab2.service.RoleService;
import honhatquang890.gmail.com.lab2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user-management")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/users/user-management";
//        return "watthe";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "/users/user-edit";
    }

    @PostMapping("/edit/{id}")   /* public String updateUser(@RequestParam Long id, @RequestParam String role, Principal principal, Model model) {

        try {
            Role newRole = Role.valueOf(role); // Convert role string to Role enum
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Check if the current user has MASTER role
            User currentUser = userService.findByUsername(principal.getName()).orElseThrow(() -> new IllegalArgumentException("Current user not found"));
            boolean isMaster = currentUser.getRoles().stream()
                    .anyMatch(r -> r.getName().equals("MASTER"));

            if (isMaster) {
                // Update roles
                user.getRoles().clear();
                user.getRoles().add(roleRepository.findByName(newRole.name())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + newRole.name())));
                userService.updateRole(user);
            } else {
                throw new AccessDeniedException("Only MASTER can update user roles");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Failed to update user role: " + e.getMessage());
            return "/users/user-edit";
        } catch (AccessDeniedException e) {
            model.addAttribute("error", e.getMessage());
            return "/users/user-edit";
        }

        return "redirect:/user-management";
    }*/
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam List<Long> roleIds) {
        userService.updateUserRoles(id, roleIds);
        return "redirect:/user-management";
    }
}
