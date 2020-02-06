package example.repositories;

import example.domain.Pet;
import example.domain.PetDTO;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class PetRepositoryTest {

    @Inject
    PetRepository petRepository;

    @Test
    void testRetrievePetAndOwner() {
        Pet dino = petRepository.findByName("Dino").orElse(null);
        assertNotNull(dino);
        assertEquals("Dino", dino.getName());
        assertEquals("Fred", dino.getOwner().getName());
    }

    @Test
    void testRetrievePetAndOwnerId() {
        PetDTO dino = petRepository.getByName("Dino").orElse(null);
        assertNotNull(dino);
        assertEquals("Dino", dino.getName());
        assertNotNull(dino.getOwner().getId());
    }

    @Test
    void testRetrievePetDTOByID() {
        PetDTO dino = petRepository.getByName("Dino").orElse(null);
        assertNotNull(dino);
        PetDTO dinoById = petRepository.getById(dino.getId()).orElse(null);
        assertNotNull(dinoById);
    }
}
