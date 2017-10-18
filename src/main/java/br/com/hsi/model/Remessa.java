package br.com.hsi.model;

import br.com.hsi.model.dados.text.StatusRemessa;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe com atributos para criação de remessa de produtos
 *
 * @author Eriel Miquilino
 */

@Entity
@Table(name = "remessa")
public class Remessa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "id_representante")
    private Entidade representante;

    @OneToMany(mappedBy = "remessa")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RemessaItem> remessaItens = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "id_praca")
    private Praca praca;

    private LocalDateTime emissao;

    @Column(name = "status_remessa")
    @Enumerated(EnumType.STRING)
    private StatusRemessa statusRemessa;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private BigDecimal total = new BigDecimal(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Entidade getRepresentante() {
        return representante;
    }

    public void setRepresentante(Entidade representante) {
        this.representante = representante;
    }

    public List<RemessaItem> getRemessaItens() {
        return remessaItens;
    }

    public void setRemessaItens(List<RemessaItem> remessaItens) {
        this.remessaItens = remessaItens;
    }

    public LocalDateTime getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDateTime emissao) {
        this.emissao = emissao;
    }

    public StatusRemessa getStatusRemessa() {
        return statusRemessa;
    }

    public void setStatusRemessa(StatusRemessa statusRemessa) {
        this.statusRemessa = statusRemessa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Praca getPraca() {
        return praca;
    }

    public void setPraca(Praca praca) {
        this.praca = praca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Remessa remessa = (Remessa) o;

        return id != null ? id.equals(remessa.id) : remessa.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
