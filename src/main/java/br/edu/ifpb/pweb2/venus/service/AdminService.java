package br.edu.ifpb.pweb2.venus.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.pweb2.venus.model.Aluno;
import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Colegiado;
import br.edu.ifpb.pweb2.venus.model.Curso;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.repository.AlunoRepository;
import br.edu.ifpb.pweb2.venus.repository.AssuntoRepository;
import br.edu.ifpb.pweb2.venus.repository.ColegiadoRepository;
// import br.edu.ifpb.pweb2.venus.repository.CursoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProfessorRepository;

@Service
public class AdminService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository; 
    
    // @Autowired
    // private CursoRepository cursoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Transactional
    public void removerAluno(Long id) {
        alunoRepository.deleteById(id);
    }
    @Transactional
    public void saveAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listAluno() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> getAluno(Long id) {
        return alunoRepository.findById(id);
    }

    @Transactional
    public void removerProfessor(Integer id) {
        professorRepository.deleteById(id);
    }

    @Transactional
    public void salvarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessor(Integer id) {
        return professorRepository.findById(id);
    }
    
    @Transactional
    public void removerColegiado(Integer id) {
        colegiadoRepository.deleteById(id);
    }

    @Transactional
    public void salvarColegiado(Colegiado colegiado) {
        colegiadoRepository.save(colegiado);
    }

    public List<Colegiado> listarColegiado() {
        return colegiadoRepository.findAll();
    }

    public Optional<Colegiado> getColegiado(Integer id) {
        return colegiadoRepository.findById(id);
    }

    @Transactional
    public void adicionarMembro(Integer ColegiadoId, Integer professorId) {
        Colegiado colegiado = colegiadoRepository.findById(ColegiadoId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        colegiado.getMembros().add(professor);
        professor.setColegiado(colegiado);
        salvarColegiado(colegiado);
    }

    @Transactional
    public void deletarMembro(Integer ColegiadoId, Integer professorId) {
        Colegiado colegiado = colegiadoRepository.findById(ColegiadoId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        colegiado.getMembros().remove(professor);
        professor.setColegiado(null);
        professorRepository.save(professor);
        colegiadoRepository.save(colegiado);
    }


    // @Transactional
    // public void salvarCurso(Curso curso) {
    //     cursoRepository.save(curso);
    // }

    // @Transactional
    // public void removerCurso(Long id) {
    //     cursoRepository.deleteById(id);

    // }

    // @Transactional
    // public void updateCurso(Curso curso) {
    //     cursoRepository.save(curso);
    // }

    // public List<Curso> listarCursos() {
    //     return cursoRepository.findAll();
    // }

    // public Curso getCurso(Long id) {
    //     return cursoRepository.findById(id).orElse(null);
    // }

    @Transactional
    public void removerAssunto(Integer id) {
        assuntoRepository.deleteById(id);
    }
    @Transactional
    public void saveAssunto(Assunto assunto) {
        assuntoRepository.save(assunto);
    }

    public List<Assunto> listAssunto() {
        return assuntoRepository.findAll();
    }

    public Optional<Assunto> getAssunto(Integer id) {
        return assuntoRepository.findById(id);
    }
}
