package example.domain;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class PetDTO {
    private Long id;
    private String name;
    private EntityReference owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityReference getOwner() {
        return owner;
    }

    public void setOwner(EntityReference owner) {
        this.owner = owner;
    }
}
