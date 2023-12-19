package br.edu.ifpb.pweb2.venus.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.venus.model.Assunto;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.repository.AssuntoRepository;
import br.edu.ifpb.pweb2.venus.repository.ProcessoRepository;
import br.edu.ifpb.pweb2.venus.service.AlunoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @GetMapping("/processos")
    public ModelAndView getProcessos(ModelAndView mav, Principal principal) {
        mav.setViewName("alunos/listProcesso");
        mav.addObject("processos", alunoService.consultaProcessos(principal));
        return mav;
    }

    @ModelAttribute("assuntoItems")
    public List<Assunto> getAssuntos() {
        return alunoService.listAssunto();
    }

    @GetMapping("/processos/cadastro")
    public ModelAndView getCadastroAluno(ModelAndView mav) {
        mav.setViewName("alunos/formProcesso");
        mav.addObject("processo", new Processo(new Assunto()));
        return mav;
    }

    @PostMapping("/processos")
    public ModelAndView saveAluno(@Valid Processo processo, Principal principal, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()) {
            mav.setViewName("alunos/formProcesso");
            mav.addObject("processo", processo);
            return mav;
        }
        processo.setNumero("" + System.currentTimeMillis());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username: "+ principal);
        alunoService.saveProcesso(processo, username);
        mav.setViewName("redirect:/alunos/processos");
        mav.addObject("processos", alunoService.listProcesso());
        return mav;
    }

    @GetMapping("/processos/{id}")
    public ModelAndView editAluno(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("alunos/formProcesso");
        mav.addObject("processo", alunoService.getProcesso(id));
        return mav;
    }

//     @GetMapping("/processos")
//     public String processos(Model model) {
//     List<Processo> processos = processoService.listProcessos();
//     model.addAttribute("processos", processos);
//     return "professores/listProcessosProf";
// }

@RequestMapping("/processos/qm")
public String queryMethods(String tipo, Integer assuntoId, Model model) {
    List<Processo> processos = null;
    switch (tipo) {
        case "findByAssunto":
            Assunto assunto = assuntoRepository.findById(assuntoId).orElse(null);
            if (assunto != null) {
                processos = processoRepository.findByAssunto(assunto);
            }
            break;
        // Outros casos de consulta
        default:
            break;
    }
    model.addAttribute("processos", processos);
    return "alunos/listProcesso";
}


}
