package br.edu.ifpb.pweb2.venus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.venus.model.Curso;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Voto;
import br.edu.ifpb.pweb2.venus.service.AdminService;
import br.edu.ifpb.pweb2.venus.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProfessorService professorService;   
    

    @PostMapping("/processos")
    public ModelAndView saveProfessor(@Valid Processo processo, BindingResult result, ModelAndView mav){
        if (result.hasErrors()){
            mav.setViewName("professores/formProcessoProf");
            mav.addObject("processo", processo);
            return mav;
        }
        professorService.vote(processo);
        mav.setViewName("redirect:/professores/processos");
        mav.addObject("processos", professorService.listProcessos());
        return mav;
    }


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

    @ModelAttribute("cursoItems")
    public List<Curso> getCursos() {
        return adminService.listarCursos();
    }

    @ModelAttribute("votoItems")
    public List<Voto> getVotos() {
        return adminService.listVotos();
    }

    @GetMapping("processos/cadastro")
    public ModelAndView processoVotar(Processo processo,ModelAndView mav) {
        professorService.vote(processo);
        mav.addObject("processo", new Processo());
        mav.setViewName("redirect:/professores/processos");
        return mav;
    }

}
