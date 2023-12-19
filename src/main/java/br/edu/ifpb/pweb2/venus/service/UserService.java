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
    
    @Autowired
    private UserRepository userRepository;

    public String userHome(Principal principal) {
        User user = userRepository.findById(principal.getName()).get();
        List<Authority> authorities = user.getAuthorities();

        System.out.println(authorities.get(0).getAuthority());
        String result;
        if (authorities.get(0).getAuthority().equals("ROLE_COORDENADOR")) {
            result = "/coordenador/reunioes";
            
        }
        else if (authorities.get(0).getAuthority().equals("ROLE_ALUNO")) {
            result = "/alunos/processos";
        }
        else if (authorities.get(0).getAuthority().equals("ROLE_PROFESSOR")){
            result = "/professores/processos";
        }
        else {
            result = "/admin/home";
        }

        return result;
    }

}
