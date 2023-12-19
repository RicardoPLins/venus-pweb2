package br.edu.ifpb.pweb2.venus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.venus.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

    Professor findByLogin(String login);

    List<Professor> findAllByCursoId(Integer id);
    

}
