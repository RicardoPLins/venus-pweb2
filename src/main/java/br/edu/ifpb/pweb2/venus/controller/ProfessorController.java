package br.edu.ifpb.pweb2.venus.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.venus.model.Curso;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.model.StatusReuniao;
import br.edu.ifpb.pweb2.venus.repository.ReuniaoRepository;
import br.edu.ifpb.pweb2.venus.service.AdminService;
import br.edu.ifpb.pweb2.venus.service.CoordenadorService;
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

    
    @Autowired
    private CoordenadorService coordenadorService;  
    
    @Autowired
    private ReuniaoRepository reuniaoRepository;  
    

    @PostMapping("/processos")
    public ModelAndView saveProfessor(@Valid Processo processo, Principal principal, BindingResult result, ModelAndView mav){
        if (result.hasErrors()){
            mav.setViewName("professores/formProcessoProf");
            mav.addObject("processo", processo);
            return mav;
        }
        professorService.vote(processo);
        // attr.addFlashAttribute("mensagem", "Voto feito com sucesso!");
        mav.setViewName("redirect:/professores/processos");
        // mav.setViewName("redirect:professores/listProcessosProf");
        return mav;
    }


    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav, Principal principal) {
        mav.setViewName("professores/listProcessosProf");
        mav.addObject("processos", professorService.listProcessosDesignados(principal));
        return mav;
    }

    @GetMapping("/processos/{id}")
    public ModelAndView editProfessor(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes attr) {
        attr.addFlashAttribute("mensagem", "Voto feito com sucesso!");
        mav.setViewName("professores/formProcessoProf");
        mav.addObject("processo", professorService.getProcesso(id));
        return mav;
    }

    @ModelAttribute("cursoItems")
    public List<Curso> getCursos() {
        return adminService.listarCursos();
    }

    @GetMapping("/reunioes")
    public ModelAndView getProcessos(ModelAndView mav, Principal principal) {
        mav.setViewName("professores/listReuniao");
        mav.addObject("reunioes", coordenadorService.listReunioes());
        return mav;
    }

        
    // @RequestMapping("/reunioes/qm")
    // public String queryMethods(String tipo, Integer reuniaoId, Model model) {
    //     List<Reuniao> reunioes = null;
    //     switch (tipo) {
    //         case "findByStatus":
    //             StatusReuniao status = StatusReuniao.findByStatus;
    //             if (status != null) {
    //                 reunioes = reuniaoRepository.findByStatus(status);
    //             }
    //             break;
    //         // Outros casos de consulta
    //         default:
    //             break;
    //     }
    //     model.addAttribute("reunioes", reunioes);
    //     return "professores/listReuniao";
    // }



    @RequestMapping("/reunioes/qm")
    public String queryReunioes(String tipo, String status, Model model) {
        List<Reuniao> reunioes = null;
        
        if ("findByStatus".equals(tipo) && status != null && !status.isEmpty()) {
            switch (status.toLowerCase()) {
                case "programada":
                case "encerrada":
                    reunioes = reuniaoRepository.findByStatus(StatusReuniao.valueOf(status.toUpperCase()));
                    break;
                // Outros casos de consulta, se necessário
                default:
                    break;
            }
        } else {
            // Lógica para outras consultas ou para exibir todas as reuniões
            reunioes = reuniaoRepository.findAll();
        }
        
        model.addAttribute("reunioes", reunioes);
        return "professores/listReuniao"; // Substitua pelo nome correto da sua página
    }


        
    @ModelAttribute("status")
        public List<StatusReuniao> getStatus() {
            return List.of(StatusReuniao.values());
        }
    
}

