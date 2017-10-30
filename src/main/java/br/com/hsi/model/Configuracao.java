package br.com.hsi.model;

import org.hibernate.validator.constraints.Email;

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

    @Column(name = "tipo_certificado")
    private String tipoCertificado;

    @Email
    private String email;

    private String senha;

    @Column(name = "servidor_smtp")
    private String servidorSMTP;

    @Column(name = "porta_smtp")
    private int portaSMTP;

    @Column(name = "usa_ssl")
    private boolean usaSSL;

    @Column(name = "requer_autenticacao")
    private boolean requerAutenticacao;

}
