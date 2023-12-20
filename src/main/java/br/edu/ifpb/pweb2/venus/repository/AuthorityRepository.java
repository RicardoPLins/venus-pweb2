package br.edu.ifpb.pweb2.venus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.venus.model.Authority;
import br.edu.ifpb.pweb2.venus.model.Authority.AuthorityId;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId> {
    
}
