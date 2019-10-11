package example.repositories;

import example.domain.NameDTO;
import example.domain.Pet;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testRetrievePetNames() {
        List<NameDTO> petNamesAsList = petRepository.list(Pageable.UNPAGED);
        assertTrue(petNamesAsList.get(0) instanceof NameDTO);
        Page<NameDTO> petNamesAsPage = petRepository.queryAll(Pageable.UNPAGED);
        NameDTO name = petNamesAsPage.iterator().next();
        assertTrue(name instanceof NameDTO);
    }

}
