package br.edu.ifpb.pweb2.venus.model;

public enum TipoVoto {
    COM_RELATOR("com_relator"), DIVERGENTE("divergente");

    private String descricao;

    TipoVoto(String descricao){
        this.descricao = descricao;
    }

}
