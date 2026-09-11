package com.example.clyvovetnexus.controller;

import com.example.clyvovetnexus.entity.EventoPreventivo;
import com.example.clyvovetnexus.service.EventoPreventivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos-preventivos")
public class EventoPreventivoController {

    private final EventoPreventivoService service;

    public EventoPreventivoController(EventoPreventivoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventoPreventivo> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoPreventivo> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventoPreventivo criar(@RequestBody EventoPreventivo evento) {
        return service.criar(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoPreventivo> atualizar(@PathVariable Long id, @RequestBody EventoPreventivo dados) {
        return service.atualizar(id, dados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}