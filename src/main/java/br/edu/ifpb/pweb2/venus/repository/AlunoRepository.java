package br.edu.ifpb.pweb2.venus.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.edu.ifpb.pweb2.venus.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findByNome(String nome);
    
}
