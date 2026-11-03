package com.example.clyvovetnexus;

import com.example.clyvovetnexus.entity.Pet;
import com.example.clyvovetnexus.repository.PetRepository;
import com.example.clyvovetnexus.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void deveCriarPetComSucesso() {
        Pet pet = new Pet(null, "Zeus", "Cachorro", "Pastor Alemão", 4);
        Pet petSalvo = new Pet(1L, "Zeus", "Cachorro", "Pastor Alemão", 4);

        when(petRepository.save(any(Pet.class))).thenReturn(petSalvo);

        Pet resultado = petService.criar(pet);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Zeus", resultado.getNome());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void deveBuscarPetPorId() {
        Pet pet = new Pet(1L, "Pandora", "Gato", "Maine Coon", 2);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> resultado = petService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Pandora", resultado.get().getNome());
    }
}
