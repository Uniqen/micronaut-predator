package example.api;

import example.domain.NameDTO;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client("/pets")
public interface PetClient {

    @Get("/{?pageable*}")
    List<NameDTO> all(Pageable pageable);

}
