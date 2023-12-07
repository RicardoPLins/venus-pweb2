package br.edu.ifpb.pweb2.venus.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluno extends Usuario{

    public Reuniao consultarReuniao(Reuniao reuniao) {
        return reuniao;
    }

    private String cpf;
    

    @OneToMany(mappedBy = "participante")
    private List<Processo> processos;

    public void adicionarProcesso(Processo processo) {
        this.processos.add(processo);
    }
}

