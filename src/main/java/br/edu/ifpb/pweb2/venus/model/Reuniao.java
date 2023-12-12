package br.edu.ifpb.pweb2.venus.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reuniao {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Date dataReuniao;

    private StatusReuniao status;

    private byte[] ata;

    @ManyToOne
    private Colegiado colegiado;

    @OneToMany
    private List<Processo> processos;
    
    public void addProcesso(Processo processo){
        this.processos.add(processo);
    }
}
