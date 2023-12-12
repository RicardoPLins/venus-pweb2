package br.edu.ifpb.pweb2.venus.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Colegiado {
    //id: int, dataInicio: date, dataFim: date, descricao: str,
    //portaria: str, curso: Curso

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataFim;

    @NotBlank(message = "Campo obrigatório")
    private String descricao;

    @NotBlank(message = "Campo obrigatório")
    private String portaria;

    @ManyToOne
    @JoinColumn(name="curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "colegiado")
    private List<Professor> membros;

    // private Professor membro1;

    // private Professor membro2;

}
