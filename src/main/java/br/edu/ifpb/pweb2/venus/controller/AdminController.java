package br.edu.ifpb.pweb2.venus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.venus.model.Aluno;
import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Colegiado;
import br.edu.ifpb.pweb2.venus.model.Curso;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.model.User;
import br.edu.ifpb.pweb2.venus.model.Voto;
import br.edu.ifpb.pweb2.venus.repository.UserRepository;
import br.edu.ifpb.pweb2.venus.service.AdminService;
import br.edu.ifpb.pweb2.venus.ui.NavPage;
import br.edu.ifpb.pweb2.venus.ui.NavePageBuilder;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String showHomePage() {
        return "admin/home";
    }

    @GetMapping("/usuarios")
    public ModelAndView getUsuarios(ModelAndView mav) {
        mav.setViewName("admin/listUsuario");
        return mav;
    }

    @ModelAttribute("users")
    public List<User> getUserOptions(){
        return userRepository.findByEnabledTrue();
    }
    
    @GetMapping("/alunos")
    public ModelAndView getAlunos(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Aluno> pageAlunos = adminService.listAluno(paging);
        NavPage navPage = NavePageBuilder.newNavPage(pageAlunos.getNumber() + 1,
                pageAlunos.getTotalElements(), pageAlunos.getTotalPages(), size);
        mav.setViewName("admin/listAluno");
        mav.addObject("alunos", pageAlunos);
        mav.addObject("navPage", navPage);
        return mav;
    }

    @GetMapping("/alunos/cadastro")
    public ModelAndView getCadastroAluno(ModelAndView mav) {
        mav.setViewName("admin/formAluno");
        mav.addObject("aluno", new Aluno());
        return mav;
    }

    @PostMapping("/alunos")
    public ModelAndView saveAluno(@Valid Aluno aluno, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/formAluno");
            mav.addObject("aluno", aluno);
            return mav;
        }
        adminService.saveAluno(aluno);
        mav.setViewName("redirect:/admin/alunos");
        mav.addObject("alunos", adminService.listAluno());
        return mav;
    }

    @GetMapping("/alunos/{id}")
    public ModelAndView editAluno(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formAluno");
        mav.addObject("aluno", adminService.getAluno(id));
        return mav;
    }

    @GetMapping("/alunos/{id}/delete")
    public ModelAndView deleteById(@PathVariable(value = "id") Integer id, ModelAndView mav
    , RedirectAttributes attr) {
        adminService.removerAluno(id);
        attr.addFlashAttribute("mensagem", "Aluno removido com sucesso!");
        mav.setViewName("redirect:/admin/alunos");
        return mav;
    }

    @GetMapping("/professores")
    public ModelAndView getProfessores(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
            Pageable paging = PageRequest.of(page - 1, size);
        Page<Professor> pageProfessores = adminService.listProfessor(paging);
        NavPage navPage = NavePageBuilder.newNavPage(pageProfessores.getNumber() + 1,
                pageProfessores.getTotalElements(), pageProfessores.getTotalPages(), size);
        mav.setViewName("admin/listProfessor");
        mav.addObject("professores", pageProfessores);
        mav.addObject("navPage", navPage);
        return mav;
    }

    @GetMapping("/professores/cadastro")
    public ModelAndView getCadastroProfessor(ModelAndView mav) {
        mav.setViewName("admin/formProfessor");
        mav.addObject("professor", new Professor());
        return mav;
    }

    @PostMapping("/professores")
    public ModelAndView saveProfessor(@Valid Professor professor, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/formProfessor");
            mav.addObject("professor", professor);
            return mav;
        }
        adminService.salvarProfessor(professor);
        mav.setViewName("redirect:/admin/professores");
        mav.addObject("professores", adminService.listarProfessores());
        return mav;
    }

    @GetMapping("/professores/{id}")
    public ModelAndView editProfessor(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formProfessor");
        mav.addObject("professor", adminService.getProfessor(id));
        return mav;
    }

    @GetMapping("/professores/{id}/delete")
    public ModelAndView deleteProfessor(@PathVariable(value = "id") Integer id,
     ModelAndView mav, RedirectAttributes attr) {
        adminService.removerProfessor(id);
        attr.addFlashAttribute("mensagem", "Professor removido com sucesso!");
        mav.setViewName("redirect:/admin/professores");
        return mav;
    }

    @GetMapping("/colegiados")
public ModelAndView getColegiados(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "3") int size) {
    Pageable paging = PageRequest.of(page - 1, size);
    Page<Colegiado> pageColegiados = adminService.listColegiado(paging);
    NavPage navPage = NavePageBuilder.newNavPage(pageColegiados.getNumber() + 1,
            pageColegiados.getTotalElements(), pageColegiados.getTotalPages(), size);
    mav.setViewName("admin/listColegiado");
    mav.addObject("colegiados", pageColegiados);
    mav.addObject("navPage", navPage);
    return mav;
}

    @GetMapping("/colegiados/{id}")
    public ModelAndView editarColegiado(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formColegiado");
        mav.addObject("colegiado", adminService.getColegiado(id));
        return mav;
    }

    @GetMapping("/colegiados/cadastro")
    public ModelAndView getCadastroColegiado(ModelAndView mav) {
        mav.setViewName("admin/formColegiado");
        mav.addObject("colegiado", new Colegiado());
        return mav;
    }

    @PostMapping("/colegiados")
    public ModelAndView saveColegiado(@Valid Colegiado colegiado,BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/formColegiado");
            mav.addObject("colegiado", colegiado);
            return mav;
        }
        adminService.salvarColegiado(colegiado);
        mav.setViewName("redirect:/admin/colegiados");
        mav.addObject("colegiados", adminService.listarColegiado());
        return mav;
    }

    @GetMapping("/colegiados/{id}/delete")
    public ModelAndView deleteColegiado(@PathVariable(value = "id") Integer id,
     ModelAndView mav, RedirectAttributes attr) {
        adminService.removerColegiado(id);
        attr.addFlashAttribute("mensagem", "Colegiado removido com sucesso!");
        mav.setViewName("redirect:/admin/colegiados");
        return mav;
    }

    @GetMapping("/colegiados/membros/{id}")
    public ModelAndView getAddMembros(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formMembro");
        mav.addObject("colegiadoId", id);
        mav.addObject("professores", adminService.listarProfessoresCurso(id));
        return mav;
    }

    @PostMapping("/colegiados/membros")
    public ModelAndView salvarMembro(Integer idColegiado, Integer idProfessor, ModelAndView mav) {
        adminService.adicionarMembro(idColegiado, idProfessor);
        mav.setViewName("redirect:/admin/colegiados/membros/" + idColegiado );
        return mav;
    }

    @DeleteMapping("/colegiados/{id}/membros/{idProfessor}")
    public ModelAndView deletarMembro(@PathVariable(value = "id") Integer idColegiado, @PathVariable(value = "idProfessor") Integer idProfessor, ModelAndView mav) {
        adminService.deletarMembro(idColegiado, idProfessor);
        mav.setViewName("/admin/formColegiado");
        mav.addObject("colegiado", adminService.getColegiado(idColegiado));
        return mav;

    }

    @GetMapping("/cursos")
public ModelAndView getCursos(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
    Pageable paging = PageRequest.of(page - 1, size);
        Page<Curso> pageCursos = adminService.listCurso(paging);
        NavPage navPage = NavePageBuilder.newNavPage(pageCursos.getNumber() + 1,
                pageCursos.getTotalElements(), pageCursos.getTotalPages(), size);
    mav.setViewName("admin/listCurso");
    mav.addObject("cursos", pageCursos);
        mav.addObject("navPage", navPage);
    return mav;
}

    @GetMapping("/cursos/cadastro")
    public ModelAndView getCadastro(ModelAndView mav) {
        mav.setViewName("admin/formCurso");
        mav.addObject("curso", new Curso());
        return mav;
    }

    @GetMapping("/cursos/{id}")
    public ModelAndView editCurso(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formCurso");
        mav.addObject("curso", adminService.getCurso(id));
        return mav;
    }
    
    @PostMapping("/cursos")
    public ModelAndView saveCurso(@Valid Curso curso, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.addObject("curso", curso);
            mav.setViewName("/admin/formCurso");
            return mav;
        }
        adminService.salvarCurso(curso);
        mav.setViewName("redirect:/admin/cursos");
        mav.addObject("cursos", adminService.listarCursos());
        return mav;
    }

    @GetMapping("/cursos/{id}/delete")
    public ModelAndView deleteCurso(@PathVariable(value = "id") Integer id,
     ModelAndView mav, RedirectAttributes attr) {
        adminService.removerCurso(id);
        attr.addFlashAttribute("mensagem", "Curso removido com sucesso!");
        mav.setViewName("redirect:/admin/cursos");
        return mav;
    }

    @ModelAttribute("cursoItems")
    public List<Curso> getCursos() {
        return adminService.listarCursos();
    }

    @ModelAttribute("professores")
    public List<Professor> getProfessores() {
        return adminService.listarProfessores();
    }

    @GetMapping("/assuntos")
    public ModelAndView getAssuntos(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Assunto> pageAssuntos = adminService.listAssunto(paging);
        NavPage navPage = NavePageBuilder.newNavPage(pageAssuntos.getNumber() + 1,
                pageAssuntos.getTotalElements(), pageAssuntos.getTotalPages(), size);
        mav.setViewName("admin/listAssunto");
        mav.addObject("assuntos", pageAssuntos);
        mav.addObject("navPage", navPage);
        return mav;
    }

    @GetMapping("/assuntos/cadastro")
    public ModelAndView getCadastroAssunto(ModelAndView mav) {
        mav.setViewName("admin/formAssunto");
        mav.addObject("assunto", new Assunto());
        return mav;
    }

    @PostMapping("/assuntos")
    public ModelAndView saveAssunto(@Valid Assunto assunto, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()) {
            mav.setViewName("admin/formAssunto");
            mav.addObject("assunto", assunto);
            return mav;
        }
        adminService.saveAssunto(assunto);
        mav.setViewName("redirect:/admin/assuntos");
        mav.addObject("assuntos", adminService.listAssunto());
        return mav;
    }

    @GetMapping("/assuntos/{id}")
    public ModelAndView editAssunto(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formAssunto");
        mav.addObject("assunto", adminService.getAssunto(id));
        return mav;
    }


    @GetMapping("/assuntos/{id}/delete")
    public ModelAndView deleteAssunto(@PathVariable(value = "id") Integer id,
     ModelAndView mav, RedirectAttributes attr) {
        adminService.removerProfessor(id);
        attr.addFlashAttribute("mensagem", "Assunto removido com sucesso!");
        mav.setViewName("redirect:/admin/assuntos");
        return mav;
    }

    @GetMapping("/votos")
    public ModelAndView getVotos(ModelAndView mav) {
        mav.setViewName("admin/listVoto");
        mav.addObject("votos", adminService.listVotos());
        return mav;
    }

    @GetMapping("/votos/cadastro")
    public ModelAndView getCadastroVoto(ModelAndView mav) {
        mav.setViewName("admin/formVoto");
        mav.addObject("voto", new Voto());
        return mav;
    }

    @PostMapping("/votos")
    public ModelAndView saveVoto(@Valid Voto voto, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()) {
            mav.setViewName("admin/formVoto");
            mav.addObject("voto", voto);
            return mav;
        }
        adminService.saveVoto(voto);
        mav.setViewName("redirect:/admin/votos");
        mav.addObject("votos", adminService.listVotos());
        return mav;
    }

    @GetMapping("/votos/{id}")
    public ModelAndView editVoto(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("admin/formVoto");
        mav.addObject("voto", adminService.getVoto(id));
        return mav;
    }

    @GetMapping("/votos/{id}/delete")
    public ModelAndView deleteVoto(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes attr) {
        adminService.removerVoto(id);
        attr.addFlashAttribute("mensagem", "Voto removido com sucesso!");
        mav.setViewName("redirect:/admin/votos");
        return mav;
    }

}
