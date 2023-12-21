package br.edu.ifpb.pweb2.venus.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message="Campo obrigatório")
    @Size(min = 3, max = 60)
    private String nome;

    @NotBlank(message="Campo obrigatório")
    private String fone;
    
    @NotBlank(message="Campo obrigatório")
    @Matricula
    private String matricula;
    
    @NotBlank(message="Campo obrigatório")
    // @Email(message = "email inválido")
    private String login;

    @NotBlank(message="Campo obrigatório")
    private String senha;

    private boolean admin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name="curso_id")
    private Curso curso;

    // @OneToOne
    // @JoinColumn(name = "id_aluno")
    // private Aluno aluno;


    // public Usuario(Aluno aluno) {
    //     this.aluno = aluno;
    // }

}
