package br.edu.ifpb.pweb2.venus.model;

import java.util.Date;

public class Reuniao {
    //@Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Date dataReuniao;

    private StatusReuniao status;

    private byte[] ata;
}
