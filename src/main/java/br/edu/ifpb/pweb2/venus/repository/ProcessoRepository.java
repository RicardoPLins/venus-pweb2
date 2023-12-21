package br.edu.ifpb.pweb2.venus.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.venus.model.Aluno;
import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.StatusEnum;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Integer>{

    public List<Processo> findAllByParticipanteLogin(String login);

    public List<Processo> findByAssunto(Assunto assunto);

    public List<Processo> findByStatus(StatusEnum status);

    public List<Processo> findByRelatorLogin(String login);

    // public List<Processo> findByParticipanteAndAssuntoIdAndStatus(Aluno aluno, Integer assuntoId, String status, Sort sort);

    // public List<Processo> findByParticipanteAndAssuntoId(Aluno aluno, Integer assuntoId, Sort sort);

    // public List<Processo> findByParticipanteAndStatus(Aluno aluno, String status, Sort sort);

    // public List<Processo> findByParticipante(Aluno aluno, Sort sort);

}
