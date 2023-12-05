package br.edu.ifpb.pweb2.venus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.repository.ProcessoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProfessorRepository;
import jakarta.transaction.Transactional;

@Service
public class ProfessorService{
    
   @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    public List<Processo> listProcessoDesignados() {
        return processoRepository.findAll();
    }

    public Processo buscarProcesso(Integer id) {
        return processoRepository.findById(id).get();
    }

   
}
