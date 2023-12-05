package br.edu.ifpb.pweb2.venus.model;

public enum StatusReuniao {

    ENCERRADA("encerrada"), PROGRAMADA("programada");

    private String descricao;

    StatusReuniao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}

