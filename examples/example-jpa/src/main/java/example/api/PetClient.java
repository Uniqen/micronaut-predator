package example.api;

import example.domain.Pet;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;


@Client("/pets")
public interface PetClient {

    @Get("/")
    Page<Pet> all(@QueryValue("page") int number, @QueryValue("size") int size);

    @Get("/")
    Page<Pet> all(@QueryValue("page") int number, @QueryValue("size") int size, @QueryValue("sort") String sort);

}
