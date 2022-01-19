package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;


import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@MappedSuperclass
@Getter
public abstract class BasicEntity implements Serializable {

    //spring.jpa.properties.hibernate.auto_quote_keyword=true // where to add?
    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator(name = "uuid",
    strategy = "org.hibernate.id.UUIDGenerator")

    @Access (AccessType.FIELD) // default
    private String id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity that = (BasicEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
