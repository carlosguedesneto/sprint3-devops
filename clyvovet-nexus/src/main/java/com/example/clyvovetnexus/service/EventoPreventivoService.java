package com.example.clyvovetnexus.service;

import com.example.clyvovetnexus.entity.EventoPreventivo;
import com.example.clyvovetnexus.repository.EventoPreventivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoPreventivoService {

    private final EventoPreventivoRepository repository;

    public EventoPreventivoService(EventoPreventivoRepository repository) {
        this.repository = repository;
    }

    public List<EventoPreventivo> listarTodos() {
        return repository.findAll();
    }

    public Optional<EventoPreventivo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EventoPreventivo criar(EventoPreventivo evento) {
        return repository.save(evento);
    }

    public Optional<EventoPreventivo> atualizar(Long id, EventoPreventivo dados) {
        return repository.findById(id).map(evento -> {
            evento.setTipoEvento(dados.getTipoEvento());
            evento.setDescricao(dados.getDescricao());
            evento.setDataRealizacao(dados.getDataRealizacao());
            evento.setDataProximoVencimento(dados.getDataProximoVencimento());
            evento.setPet(dados.getPet());
            return repository.save(evento);
        });
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}