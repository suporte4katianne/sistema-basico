package br.com.hsi.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Atributos para o cadastro de Praça de atendimento
 *
 * @author Eriel Miquilino
 */

@Entity
@Table(name = "praca")
public class Praca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @NotBlank
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Praca praca = (Praca) o;

        return id != null ? id.equals(praca.id) : praca.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
