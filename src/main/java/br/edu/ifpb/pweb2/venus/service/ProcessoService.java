package br.edu.ifpb.pweb2.venus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.venus.repository.ProcessoRepository;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

}