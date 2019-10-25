package example.api;

import example.domain.NameDTO;
import io.micronaut.data.model.PageableQuery;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client("/pets")
public interface PetClient {

    @Get("/{?pageable*}")
    List<NameDTO> all(PageableQuery pageable);

}
