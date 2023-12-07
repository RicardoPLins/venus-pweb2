package br.edu.ifpb.pweb2.venus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.venus.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    List<User> findByEnabledTrue();

    User findByUsername(String login);
    
}
