package br.edu.ifpb.pweb2.venus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.venus.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
