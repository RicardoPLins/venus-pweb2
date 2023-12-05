package br.edu.ifpb.pweb2.venus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.service.ProfessorService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;   
    
    
    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav, HttpSession session) {
        mav.setViewName("professores/formProcessos");
        mav.addObject("processos", professorService.listProcessoDesignados());
        return mav;
    }



}
