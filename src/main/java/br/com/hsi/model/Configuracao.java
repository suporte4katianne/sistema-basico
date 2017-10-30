package br.com.hsi.model;

import javax.persistence.*;

/**
 * @author Eriel Miquilino
 */
@Entity
@Table(name = "configuracao")
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] logo;
    @Lob
    @Column(name = "informacao_complementar")
    private String infoComplementar;

    private int ambiente;

    @Column(name = "fuso_horario", columnDefinition="VARCHAR(255) default '-03:00'")
    private String fusoHorario;

}
