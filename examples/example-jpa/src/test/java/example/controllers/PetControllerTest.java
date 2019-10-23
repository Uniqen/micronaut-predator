package example.controllers;

import example.api.PetClient;
import example.domain.Pet;
import io.micronaut.data.model.Page;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
class PetControllerTest {

    @Inject
    PetClient petClient;

    @Test
    void testListPetByPage() {

        Page<Pet> results = petClient.all(0, 2);

        Assertions.assertEquals(
                2,
                results.getNumberOfElements()
        );
    }

    @Test
    void testListPetBySortedPage() {

        Page<Pet> results = petClient.all(0, 2, "name,DESC");

        Assertions.assertEquals(
                2,
                results.getNumberOfElements()
        );
    }

}