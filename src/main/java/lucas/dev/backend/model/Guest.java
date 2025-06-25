package lucas.dev.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Guest {

    @Column(name = "guest_name")
    private String name;
    @Column(name = "guest_document")
    private String document;

}
