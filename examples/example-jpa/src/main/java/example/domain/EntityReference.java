package example.domain;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class EntityReference {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
