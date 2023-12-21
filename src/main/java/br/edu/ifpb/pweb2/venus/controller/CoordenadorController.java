package br.edu.ifpb.pweb2.venus.controller;

import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.venus.model.Colegiado;
import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.model.StatusReuniao;
import br.edu.ifpb.pweb2.venus.service.CoordenadorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @GetMapping("/colegiados")
    public ModelAndView getColegiado(ModelAndView mav, Principal principal) {
        mav.setViewName("coordenador/listColegiado");
        mav.addObject("colegiado", coordenadorService.listColegiado(principal));
        return mav;
    }

    @ModelAttribute("colegiados")
    public List<Colegiado> getColegiados(Principal principal) {
        return coordenadorService.listColegiado(principal);
    }


    @GetMapping("/processos")
    public ModelAndView getProcessos(ModelAndView mav, Principal principal) {
        mav.setViewName("coordenador/listProcessos");
        mav.addObject("processos", coordenadorService.listProcessos(principal));
        return mav;
    }

    @GetMapping("/processos/{id}/relator")
    public ModelAndView getProcesso(@PathVariable("id") Integer processoId, ModelAndView mav) {
        mav.setViewName("coordenador/addRelator");
        mav.addObject("processo", coordenadorService.getProcesso(processoId));
        return mav;
    }

    @PostMapping("/processos/{id}/relator")
    public ModelAndView setRelator(Processo processo, Professor relator, ModelAndView mav) {
        coordenadorService.saveProcesso(processo, relator);
        mav.setViewName("redirect:/coordenador/processos");
        return mav;
    }
        
    @ModelAttribute("relatorItens")
    public List<Professor> getProfessorRelator(Principal principal) {
        return coordenadorService.listProfessoresColegiado(principal);

    }


    @GetMapping("/reunioes")
    public ModelAndView getReunioes(ModelAndView mav) {
    mav.setViewName("coordenador/listReuniao");
    mav.addObject("reunioes", coordenadorService.listReunioes());
    return mav;
    }

    @GetMapping("/reunioes/cadastro")
    public ModelAndView getCadastroReuniao(ModelAndView mav) {
        mav.setViewName("coordenador/formReuniao");
        mav.addObject("reuniao", new Reuniao());
        return mav;
    }

    @PostMapping("/reunioes")
    public ModelAndView saveReuniao(@Valid Reuniao reuniao, Principal principal, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()) {
            mav.setViewName("coordenador/formReuniao");
            mav.addObject("reuniao", reuniao);
            return mav;
        }
        coordenadorService.saveReuniao(reuniao, principal);
        mav.setViewName("redirect:/coordenador/reunioes");
        mav.addObject("reunioes", coordenadorService.listReunioes());
        return mav;
    }

    @GetMapping("/reunioes/{id}")
    public ModelAndView editReuniao(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("coordenador/formReuniao");
        mav.addObject("reuniao", coordenadorService.getReuniao(id));
        return mav;
    }

    @GetMapping("/reunioes/{id}/delete")
    public ModelAndView deleteReuniao(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes attr) {
        coordenadorService.removerReuniao(id);
        attr.addFlashAttribute("mensagem", "Reunião removida com sucesso!");
        mav.setViewName("redirect:/coordenador/reunioes");
        return mav;
    }

    @ModelAttribute("status")
        public List<StatusReuniao> getStatus() {
            return List.of(StatusReuniao.values());
        }


    @ModelAttribute("processos")
    public List<Processo> getProcessos(Principal principal) {
        return coordenadorService.listProcessos(principal);
    }

    @GetMapping("/reunioes/{id}/iniciarSessao")
    public ModelAndView iniciarSessao(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        coordenadorService.iniciarSessao(id);
        mav.setViewName("redirect:/coordenador/reunioes/{id}/sessao");
        return mav;
    }

    @GetMapping("/reunioes/{id}/sessao")
    public ModelAndView getSessao(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        mav.setViewName("coordenador/sessao");
        mav.addObject("reuniao", coordenadorService.listProcessosPautas(id));
        return mav;
    }


    @GetMapping("/reunioes/{id}/encerrarSessao")
    public ModelAndView encerrarSessao(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        coordenadorService.encerrarSessao(id);
        mav.setViewName("redirect:/coordenador/reunioes");
        return mav;
    }
    
}
