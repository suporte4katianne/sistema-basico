package br.com.hsi.nfe.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "inutilacao")
public class Inutilizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Date data;
    @NotNull
    private int serie;
    @NotNull
    @Column(name = "numero_inicio")
    private int numeroInicio;
    @NotNull
    @Column(name = "numero_fim")
    private int numeroFim;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    private String status;
    private String motivo;
    private int ambiente;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getNumeroInicio() {
        return numeroInicio;
    }

    public void setNumeroInicio(int numeroInicio) {
        this.numeroInicio = numeroInicio;
    }

    public int getNumeroFim() {
        return numeroFim;
    }

    public void setNumeroFim(int numeroFim) {
        this.numeroFim = numeroFim;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(int ambiente) {
        this.ambiente = ambiente;
    }
}
