package br.edu.ifpb.pweb2.venus.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String numero;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dataRecepcao;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dataDistribuicao;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dataParecer;

    private byte[] parecer;

    private TipoDecisao decisaoRelator;

    @NotBlank(message = "Campo Obrigatório!")
    @Size(min=5, max = 40, message = "O requerimento deve ter no min 5 e max 40")
    private String texto;

    @OneToOne
    @JoinColumn(name = "id_assunto")
    private Assunto assunto;

    public Processo(Assunto assunto) {
        this.assunto = assunto;
    }
}