package com.example.clyvovetnexus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "eventos_preventivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoPreventivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "data_realizacao")
    private LocalDate dataRealizacao;

    @Column(name = "data_proximo_vencimento")
    private LocalDate dataProximoVencimento;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @PrePersist
    protected void onCreate() {
        if (dataRealizacao == null) {
            dataRealizacao = LocalDate.now();
        }
    }
}