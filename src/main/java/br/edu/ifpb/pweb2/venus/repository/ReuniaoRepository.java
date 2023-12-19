package br.edu.ifpb.pweb2.venus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.model.StatusReuniao;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Integer>{

    public List<Reuniao> findByStatus(StatusReuniao status);
    
    // @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id = ?1 and r.status = ?2")
    // public List<Reuniao> RenioesDeProfessoresEEstdos(Integer idProfessor, StatusReuniao status);
    


}   
