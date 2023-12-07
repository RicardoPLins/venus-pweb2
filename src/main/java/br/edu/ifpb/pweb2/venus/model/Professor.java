package br.edu.ifpb.pweb2.venus.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Usuario{
    
    private Boolean coordenador;

    // @OneToMany(mappedBy = "relator")
    // private List<Processo> processos;

    @ManyToOne
    @JoinColumn(name="colegiado_id")
    private Colegiado colegiado;

    public void consultarListaSeusProcessos(){

    }
    public void RedigirParecerProcessos(){

    }
    
}
