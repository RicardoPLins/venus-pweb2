package br.edu.ifpb.pweb2.venus.service;

import java.util.List;
import java.util.Optional;

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

    public List<Processo> listProcessos() {
        return processoRepository.findAll();
    }

    public Processo buscarProcesso(Integer id) {
        return processoRepository.findById(id).get();
    }

    public Optional<Processo> getProcesso(Integer id) {
        return processoRepository.findById(id);
    }

    @Transactional
    public void vote(Processo processo) {
        Processo processoProf = processoRepository.findById(processo.getId()).get();
        processoProf.setVoto(processo.getVoto());
        processoProf.setJustificativa(processo.getJustificativa());
        processoRepository.save(processoProf);

    }
   
}
