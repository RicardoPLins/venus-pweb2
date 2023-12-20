package br.edu.ifpb.pweb2.venus.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.pweb2.venus.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("select p.participante from Processo p join p.curso c where c.id = ?1")
    List<Aluno> findAllByAlunoAndProcesso(Integer id);

    Aluno findByLogin(String login);
    
}
