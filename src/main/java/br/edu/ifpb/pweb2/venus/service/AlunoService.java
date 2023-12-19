package br.edu.ifpb.pweb2.venus.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.pweb2.venus.model.Aluno;
import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.StatusEnum;
import br.edu.ifpb.pweb2.venus.model.TipoDecisao;
import br.edu.ifpb.pweb2.venus.repository.AlunoRepository;
import br.edu.ifpb.pweb2.venus.repository.AssuntoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProcessoRepository;

@Service
public class AlunoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public void removerProcesso(Integer id) {
        processoRepository.deleteById(id);
    }

    @Transactional
    public void saveProcesso(Processo processo, String login) {
        Aluno aluno = alunoRepository.findByLogin(login);
        aluno.adicionarProcesso(processo);

        Assunto assunto = assuntoRepository.findById(processo.getAssunto().getId()).get();
        processo.setAssunto(assunto);

        Date dataRecepcao = new Date();
        processo.setStatus(StatusEnum.CRIADO);
        processo.setTipoDecisao(TipoDecisao.DEFERIMENTO);
        processo.setParticipante(aluno);

        processo.setCurso(aluno.getCurso());
        processo.setDataRecepcao(dataRecepcao);
        
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

    public Aluno getAluno(Integer id) {
        return alunoRepository.findById(id).get();
    }

    public List<Processo> consultaProcessos(Principal user){
        return processoRepository.findAllByParticipanteLogin(user.getName());
    }

}
