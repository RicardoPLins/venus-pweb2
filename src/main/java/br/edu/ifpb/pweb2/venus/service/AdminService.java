package br.edu.ifpb.pweb2.venus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.pweb2.venus.model.Aluno;
import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Authority;
import br.edu.ifpb.pweb2.venus.model.Colegiado;
import br.edu.ifpb.pweb2.venus.model.Curso;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.model.User;
import br.edu.ifpb.pweb2.venus.model.Voto;
import br.edu.ifpb.pweb2.venus.repository.AlunoRepository;
import br.edu.ifpb.pweb2.venus.repository.AssuntoRepository;
import br.edu.ifpb.pweb2.venus.repository.AuthorityRepository;
import br.edu.ifpb.pweb2.venus.repository.ColegiadoRepository;
import br.edu.ifpb.pweb2.venus.repository.CursoRepository;
// import br.edu.ifpb.pweb2.venus.repository.CursoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProfessorRepository;
import br.edu.ifpb.pweb2.venus.repository.UserRepository;
import br.edu.ifpb.pweb2.venus.repository.VotoRepository;

@Service
public class AdminService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Transactional
    public void removerAluno(Integer id) {
        alunoRepository.deleteById(id);
    }

    @Transactional
    public void saveAluno(Aluno aluno) {
        PasswordEncoder hash = new BCryptPasswordEncoder();
        if (aluno.getId() == null) {
            // Novo registro de estudante
            String senhaCriptograda = hash.encode((CharSequence) aluno.getSenha());
            aluno.setSenha(senhaCriptograda);
            User user = new User(aluno.getLogin(), senhaCriptograda);
            user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_ALUNO")));
            user.setEnabled(true);
            aluno.setUser(user);
            alunoRepository.save(aluno);
        }
    }

    // @Transactional
    // public void saveUser(User user, Integer id) {
    // Aluno aluno = alunoRepository.findById(id).get();
    // String login = aluno.getLogin();
    // String password = aluno.getSenha();
    // user = userRepository.findByUsername(login);
    // user = new User(login, password, true, null);
    // user.addAuthority("ROLE_ALUNO");

    // userRepository.save(user);
    // }

    public List<Aluno> listAluno() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> getAluno(Integer id) {
        return alunoRepository.findById(id);
    }

    @Transactional
    public void removerProfessor(Integer id) {
        professorRepository.deleteById(id);
    }

    // @Transactional
    // public void salvarProfessor(Professor professor) {
    // professorRepository.save(professor);
    // }
    @Transactional
    public void salvarProfessor(Professor professor) {
        PasswordEncoder hash = new BCryptPasswordEncoder();

        if(professor.getCoordenador() == true){
            professor.setCoordenador(true);
            String senhaCriptograda = hash.encode((CharSequence) professor.getSenha());
            professor.setSenha(senhaCriptograda);
            User user = new User(professor.getLogin(), senhaCriptograda);
            user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_COORDENADOR")));
            user.setEnabled(true);
            professor.setUser(user);
            professorRepository.save(professor);
        }
        else{
            professor.setCoordenador(false);
            String senhaCriptograda = hash.encode((CharSequence) professor.getSenha());
            professor.setSenha(senhaCriptograda);
            User user = new User(professor.getLogin(), senhaCriptograda);
            user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_PROFESSOR")));
            user.setEnabled(true);
            professor.setUser(user);
            professorRepository.save(professor);
        }
        // if (professor.getId() == null) {
        //     // Novo registro de estudante
        //     String senhaCriptograda = hash.encode((CharSequence) professor.getSenha());
        //     professor.setSenha(senhaCriptograda);
        //     User user = new User(professor.getLogin(), senhaCriptograda);
        //     user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_PROFESSOR")));
        //     user.setEnabled(true);
        //     professor.setUser(user);
        //     professorRepository.save(professor);
        // }
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

    public  Colegiado getColegiado(Integer id) {
        return colegiadoRepository.findById(id).orElse(null);
    }

    public List<Professor> listarProfessoresCurso(Integer id) {
        Colegiado colegiado = getColegiado(id);
        List<Professor> listProfessores = new ArrayList<Professor>();
        professorRepository.findAllByCursoId(colegiado.getCurso().getId())
                .forEach(listProfessores::add);

        return listProfessores;
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

    @Transactional
    public void salvarCurso(Curso curso) {
        cursoRepository.save(curso);
    }

    @Transactional
    public void removerCurso(Integer id) {
        cursoRepository.deleteById(id);

    }

    @Transactional
    public void updateCurso(Curso curso) {
        cursoRepository.save(curso);
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso getCurso(Integer id) {
        return cursoRepository.findById(id).orElse(null);
    }

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

    public List<User> findEnabledUsers() {
        return userRepository.findByEnabledTrue();
    }

    public List<Voto> listVotos() {
        return votoRepository.findAll();
    }

    @Transactional
    public void removerVoto(Integer id) {
        votoRepository.deleteById(id);
    }

    @Transactional
    public void saveVoto(Voto voto) {
        votoRepository.save(voto);
    }

    public Optional<Voto> getVoto(Integer id) {
        return votoRepository.findById(id);
    }

    
}
