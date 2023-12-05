package br.edu.ifpb.pweb2.venus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.repository.AssuntoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProcessoRepository;

@Service
public class AlunoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Transactional
    public void removerProcesso(Integer id) {
        processoRepository.deleteById(id);
    }

    @Transactional
    public void saveProcesso(Processo processo) {
        Assunto assunto = assuntoRepository.findById(processo.getAssunto().getId()).get();
        processo.setAssunto(assunto);
        processoRepository.save(processo);
    }

    public List<Processo> listProcesso() {
        return processoRepository.findAll();
    }

    public List<Assunto> listAssunto() {
        return assuntoRepository.findAll();
    }

    public Optional<Processo> getProcesso(Integer id) {
        return processoRepository.findById(id);
    }

}
