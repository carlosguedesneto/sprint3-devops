package com.example.clyvovetnexus.service;

import com.example.clyvovetnexus.entity.Pet;
import com.example.clyvovetnexus.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> listarTodos() {
        return repository.findAll();
    }

    public Optional<Pet> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Pet criar(Pet pet) {
        return repository.save(pet);
    }

    public Optional<Pet> atualizar(Long id, Pet dados) {
        return repository.findById(id).map(pet -> {
            pet.setNome(dados.getNome());
            pet.setEspecie(dados.getEspecie());
            pet.setRaca(dados.getRaca());
            pet.setIdade(dados.getIdade());
            return repository.save(pet);
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