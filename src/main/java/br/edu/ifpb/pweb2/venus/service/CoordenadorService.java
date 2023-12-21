package br.edu.ifpb.pweb2.venus.service;

import java.security.Principal;
import java.util.Collections;
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
    public void saveReuniao(Reuniao reuniao, Principal principal) {
        reuniao.setStatus(StatusReuniao.PROGRAMADA);
        reuniao.setColegiado(colegiadoRepository.findByCursoId(professorRepository.findByLogin(principal.getName()).getCurso().getId()));
        reuniaoRepository.save(reuniao);   
    }

    public Optional<Reuniao> getReuniao(Integer id) {
        return reuniaoRepository.findById(id);
    }

    public List<Processo> getProcesosReuniao(Integer id){
        Optional<Reuniao> r = this.getReuniao(id);
        if (r.isPresent()) {
            Reuniao reuniao = r.get();
            return reuniao.getProcessos();
        } else {
            // Lida com o caso em que a reunião não é encontrada pelo ID
            return Collections.emptyList(); // Retorna uma lista vazia ou trata o erro de outra forma
        }
        

    }
    // public List<StatusReuniao> listStatusReuniaos(){
    //     return statusReuniao.findAll();
    // }

    public List<Processo> listProcessos(Principal user){
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

    public List<Colegiado> listColegiado(Principal user) {
        return colegiadoRepository.findByMembrosId(professorRepository.findByLogin(user.getName()).getId());
    }

    @Transactional
    public void saveProcesso(Processo processo) {

        Professor professor = professorRepository.findById(processo.getRelator().getId()).get();
        Assunto assunto = assuntoRepository.findById(processo.getAssunto().getId()).get();
        Aluno aluno = alunoRepository.findById(processo.getParticipante().getId()).get();

        processo.setRelator(professor);
        processo.setAssunto(assunto);
        processo.setParticipante(aluno);

        processo.setTexto(processo.getTexto());

        processo.setDataDistribuicao(new Date());
        // processo.setRelator(processo.getRelator());
        processo.setStatus(StatusEnum.DISTRIBUIDO);
        // Processo processo2 = processoRepository.findById(processo.getId()).get();
        processoRepository.save(processo);
    }

    @Transactional
    public void iniciarSessao(Integer id) {
        Reuniao reuniao = reuniaoRepository.findById(id).get();
        reuniaoRepository.save(reuniao);
    }

    public void encerrarSessao(Integer id) {
        Reuniao reuniao = reuniaoRepository.findById(id).get();
        reuniao.setStatus(StatusReuniao.ENCERRADA);
        reuniaoRepository.save(reuniao);
    }

    public List<Processo> listProcessosPautas(Integer id) {
        return null;
    }

    
    
}
