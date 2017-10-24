package br.com.hsi.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eriel Miquilino
 */

@Entity
@Table(name = "kit")
public class Kit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Entidade vendedor;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_remessa")
    private Remessa remessa;

    @ManyToOne
    @JoinColumn(name = "id_representante")
    private Entidade representante;

    @NotNull
    @OneToMany(mappedBy = "kit", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<KitItem> kitItens = new ArrayList<>();

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

    public Entidade getVendedor() {
        return vendedor;
    }

    public void setVendedor(Entidade vendedor) {
        this.vendedor = vendedor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }

    public Entidade getRepresentante() {
        return representante;
    }

    public void setRepresentante(Entidade representante) {
        this.representante = representante;
    }

    public List<KitItem> getKitItens() {
        return kitItens;
    }

    public void setKitItens(List<KitItem> kitItens) {
        this.kitItens = kitItens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kit kit = (Kit) o;

        if (codigo != kit.codigo) return false;
        if (id != null ? !id.equals(kit.id) : kit.id != null) return false;
        if (vendedor != null ? !vendedor.equals(kit.vendedor) : kit.vendedor != null) return false;
        if (data != null ? !data.equals(kit.data) : kit.data != null) return false;
        if (remessa != null ? !remessa.equals(kit.remessa) : kit.remessa != null) return false;
        if (representante != null ? !representante.equals(kit.representante) : kit.representante != null) return false;
        if (kitItens != null ? !kitItens.equals(kit.kitItens) : kit.kitItens != null) return false;
        return total != null ? total.equals(kit.total) : kit.total == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (codigo ^ (codigo >>> 32));
        result = 31 * result + (vendedor != null ? vendedor.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (remessa != null ? remessa.hashCode() : 0);
        result = 31 * result + (representante != null ? representante.hashCode() : 0);
        result = 31 * result + (kitItens != null ? kitItens.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }
}
