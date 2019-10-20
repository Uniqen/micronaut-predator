package example.controllers;

import example.api.PetClient;
import example.domain.NameDTO;
import io.micronaut.data.model.Pageable;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static io.micronaut.data.model.Sort.Order.Direction.DESC;

@MicronautTest
class PetControllerTest {

    @Inject
    PetClient petClient;

    @Test
    void testListPetNamesBySortedPaged() {

        List<NameDTO> results = petClient.all(Pageable.from(1,1).order("name", DESC));

        Assertions.assertEquals(
                1,
                results.size()
        );
    }

}