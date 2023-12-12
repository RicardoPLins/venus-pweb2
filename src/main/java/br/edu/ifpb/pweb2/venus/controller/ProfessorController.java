package br.edu.ifpb.pweb2.venus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Curso;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.service.AdminService;
import br.edu.ifpb.pweb2.venus.service.ProfessorService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProfessorService professorService;   
    
    
    // @GetMapping("/processos")
    // public ModelAndView getAlunos(ModelAndView mav, HttpSession session) {
    //     // Professor professor = (Professor) session.getAttribute("professor");
    //     mav.setViewName("alunos/listProcesso");
    //     mav.addObject("processos", alunoService.listProcesso());
    //     return mav;
    // }

    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav, HttpSession session) {
        mav.setViewName("professores/listProcessosProf");
        mav.addObject("processos", professorService.listProcessos());
        return mav;
    }

    @GetMapping("/processos/{id}")
    public ModelAndView editAluno(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("professores/formProcessoProf");
        mav.addObject("processo", professorService.getProcesso(id));
        return mav;
    }

    // @PostMapping("/processos/votos")
    // public ModelAndView votos(ModelAndView mav, HttpSession session){
    //     mav.setViewName("professores/formProcessoProf");
    //     mav.addObject("votos", professorService.listProcessoDesignados());
    //     return mav;
    // }


    @ModelAttribute("cursoItems")
    public List<Curso> getCursos() {
        return adminService.listarCursos();
    }

    @GetMapping("processos/cadastro")
    public ModelAndView processoVotar(Processo processo,ModelAndView mav) {
        professorService.vote(processo);
        mav.addObject("processo", new Processo());
        mav.setViewName("redirect:/professores/processos");
        return mav;
    }


}
