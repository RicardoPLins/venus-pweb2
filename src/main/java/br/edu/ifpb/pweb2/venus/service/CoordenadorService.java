package br.edu.ifpb.pweb2.venus.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.pweb2.venus.model.Colegiado;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.model.StatusEnum;
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

    // @Autowired
    // private StatusReuniao statusReuniao;

    public List<Reuniao> listReunioes() {
        return reuniaoRepository.findAll();
    }

    @Transactional
    public void removerReuniao(Integer id) {
        reuniaoRepository.deleteById(id);
    }

    @Transactional
    public void saveReuniao(Reuniao reuniao) {
        // Reuniao r = reuniaoRepository.findById(reuniao.getId()).get();
        // // if (r!= null){
        // //     r.setStatus(reuniao.getStatus());
        // //     r.setProcessos(reuniao.getProcessos());
        reuniaoRepository.save(reuniao);
        // }
        // else{
        //     throw new RuntimeException("Reuniao não encontrada" + reuniao.getId());
        // }
        
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
        processo = processoRepository.findById(processo.getId()).get();
        processo.setProf_relator(processo.getProf_relator());
        processo.setDataDistribuicao(new Date());
        processo.setStatus(StatusEnum.DISTRIBUIDO);
        processoRepository.save(processo);
    }

    
    
}
