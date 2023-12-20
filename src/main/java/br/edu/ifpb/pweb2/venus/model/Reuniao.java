package br.edu.ifpb.pweb2.venus.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataReuniao;

    @Enumerated(EnumType.STRING)
    private StatusReuniao status;

    public StatusReuniao getStatusReuniao() {
        return this.status;
    }

    private byte[] ata;

    @ManyToOne
    private Colegiado colegiado;

    @OneToMany
    private List<Processo> processos;
    
    public void addProcesso(Processo processo){
        this.processos.add(processo);
    }
    

}
