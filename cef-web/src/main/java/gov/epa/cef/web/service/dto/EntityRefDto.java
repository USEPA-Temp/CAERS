package gov.epa.cef.web.service.dto;

import java.util.Objects;

public class EntityRefDto {

    private Long id;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public long requireNonNull() {

        return Objects.requireNonNull(this.id);
    }
}
