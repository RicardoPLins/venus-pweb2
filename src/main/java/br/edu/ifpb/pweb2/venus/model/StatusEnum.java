package br.edu.ifpb.pweb2.venus.model;

public enum StatusEnum {
    CRIADO("CRIADO"), 
    DISTRIBUIDO("DISTRIBUIDO"), 
    EM_PAUTA("EM PAUTO"), 
    EM_JULGAMENTO("EM JULGAMENTO"), 
    JULGADO("JULGADO");

    private String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
