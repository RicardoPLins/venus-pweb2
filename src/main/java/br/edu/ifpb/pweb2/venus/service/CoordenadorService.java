package br.edu.ifpb.pweb2.venus.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.pweb2.venus.model.Aluno;
import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Colegiado;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.model.StatusEnum;
import br.edu.ifpb.pweb2.venus.model.StatusReuniao;
import br.edu.ifpb.pweb2.venus.repository.AlunoRepository;
import br.edu.ifpb.pweb2.venus.repository.AssuntoRepository;
import br.edu.ifpb.pweb2.venus.repository.ColegiadoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProcessoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProfessorRepository;
import br.edu.ifpb.pweb2.venus.repository.ReuniaoRepository;

@Service
public class CoordenadorService {

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    private AlunoRepository alunoRepository;
    // private StatusReuniao statusReuniao;

    public List<Reuniao> listReunioes() {
        return reuniaoRepository.findAll();
    }

    public Page<Reuniao> listReunioes(Pageable p) {
        return reuniaoRepository.findAll(p);
    }

    @Transactional
    public void removerReuniao(Integer id) {
        reuniaoRepository.deleteById(id);
    }

    @Transactional
    public void saveReuniao(Reuniao reuniao) {
        reuniao.setStatus(StatusReuniao.PROGRAMADA);
        reuniaoRepository.save(reuniao);   
    }

    public Optional<Reuniao> getReuniao(Integer id) {
        return reuniaoRepository.findById(id);
    }

    // public List<StatusReuniao> listStatusReuniaos(){
    //     return statusReuniao.findAll();
    // }

    public List<Processo> listProcessos(){
        return processoRepository.findAll();
    }

    public Page<Processo> listProcessos(Pageable p) {
        return processoRepository.findAll(p);
    }

    public Processo getProcesso(Integer processoId) {
        return processoRepository.findById(processoId).get();
    }

    public List<Professor> listProfessoresColegiado(Principal user) {
        Professor coordenador = professorRepository.findByLogin(user.getName());
        Colegiado colegiado = colegiadoRepository.findByCursoId(coordenador.getCurso().getId());
        return colegiado.getMembros();

    }

    @Transactional
    public void saveProcesso(Processo processo) {

        Professor professor = professorRepository.findById(processo.getProf_relator().getId()).get();
        Assunto assunto = assuntoRepository.findById(processo.getAssunto().getId()).get();
        Aluno aluno = alunoRepository.findById(processo.getParticipante().getId()).get();

        processo.setProf_relator(professor);
        processo.setAssunto(assunto);
        processo.setParticipante(aluno);

        processo.setTexto(processo.getTexto());

        processo.setDataDistribuicao(new Date());
        processo.setStatus(StatusEnum.DISTRIBUIDO);
        // Processo processo2 = processoRepository.findById(processo.getId()).get();
        processoRepository.save(processo);
    }

    
    
}
