package br.edu.ifpb.pweb2.venus.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.venus.model.Processo;
import br.edu.ifpb.pweb2.venus.model.Professor;
import br.edu.ifpb.pweb2.venus.model.Reuniao;
import br.edu.ifpb.pweb2.venus.model.StatusReuniao;
import br.edu.ifpb.pweb2.venus.service.AdminService;
import br.edu.ifpb.pweb2.venus.service.CoordenadorService;
import br.edu.ifpb.pweb2.venus.ui.NavPage;
import br.edu.ifpb.pweb2.venus.ui.NavePageBuilder;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private AdminService adminService;


    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "4") int size) {
    Pageable paging = PageRequest.of(page - 1, size);
    Page<Processo> pageProcessos = coordenadorService.listProcessos(paging);
    NavPage navPage = NavePageBuilder.newNavPage(pageProcessos.getNumber() + 1,
            pageProcessos.getTotalElements(), pageProcessos.getTotalPages(), size);
    mav.setViewName("coordenador/listProcessos");
    mav.addObject("processos", pageProcessos);
    mav.addObject("navPage", navPage);
    return mav;
    }

    @GetMapping("/processos/{id}/relator")
    public ModelAndView getProcesso(@PathVariable("id") Integer processoId, ModelAndView mav) {
        mav.setViewName("coordenador/addRelator");
        mav.addObject("processo", coordenadorService.getProcesso(processoId));
        return mav;
    }

    @PostMapping("/processos/{id}/relator")
    public ModelAndView setRelator(Processo processo, ModelAndView mav) {
        coordenadorService.saveProcesso(processo);
        mav.setViewName("redirect:/coordenador/processos");
        return mav;
    }
        
    @ModelAttribute("relatorItens")
    public List<Professor> getProfessorRelator(Principal principal) {
        return coordenadorService.listProfessoresColegiado(principal);

    }

    @ModelAttribute("relator")
    public List< Professor> listProfessores(){
        return adminService.listarProfessores();
    }


    @GetMapping("/reunioes")
    public ModelAndView getReunioes(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "2") int size) {
    Pageable paging = PageRequest.of(page - 1, size);
    Page<Reuniao> pageReunioes = coordenadorService.listReunioes(paging);
    NavPage navPage = NavePageBuilder.newNavPage(pageReunioes.getNumber() + 1,
            pageReunioes.getTotalElements(), pageReunioes.getTotalPages(), size);
    mav.setViewName("coordenador/listReuniao");
    mav.addObject("reunioes", pageReunioes);
    mav.addObject("navPage", navPage);
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

    @ModelAttribute("status")
        public List<StatusReuniao> getStatus() {
            return List.of(StatusReuniao.values());
        }


    @ModelAttribute("processos")
    public List<Processo> getProcessos() {
        return coordenadorService.listProcessos();
    }
    
}
