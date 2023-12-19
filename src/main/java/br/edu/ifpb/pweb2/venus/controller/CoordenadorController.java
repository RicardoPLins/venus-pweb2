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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.service.CoordenadorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;




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
    public ModelAndView saveReuniao(@Valid Reuniao reuniao, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()) {
            mav.setViewName("coordenador/formReuniao");
            mav.addObject("reuniao", reuniao);
            return mav;
        }
        coordenadorService.saveReuniao(reuniao);
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


    // @ModelAttribute("status")
    // public List<StatusReuniao> getStatus() {
    //     return coordenadorService.listStatusReuniaos();
    // }


    @ModelAttribute("processos")
    public List<Processo> getProcessos() {
        return coordenadorService.listProcessos();
    }
    
}
