package br.edu.ifpb.pweb2.venus.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.venus.model.Authority;
import br.edu.ifpb.pweb2.venus.model.User;
import br.edu.ifpb.pweb2.venus.repository.UserRepository;
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String home(Principal principal) {
        User user = userRepository.findById(principal.getName())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        String authority = getHighestAuthority(user);
        return determineRedirectPath(authority);
    }

    private String getHighestAuthority(User user) {
        List<Authority> authorities = user.getAuthorities();
        if (authorities.isEmpty()) {
            throw new RuntimeException("User has no authorities");
        }
        return authorities.get(0).getAuthority();
    }

    private String determineRedirectPath(String authority) {
        switch (authority) {
            case "ROLE_COORDENADOR":
                return "/coordenador/reunioes";
            case "ROLE_ALUNO":
                return "/alunos/processos";
            case "ROLE_PROFESSOR":
                return "/professores/processos";
            default:
                return "/admin/home";
        }
    }
}
