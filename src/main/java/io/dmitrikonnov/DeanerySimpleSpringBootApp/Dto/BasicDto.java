package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;


public abstract class BasicDto implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @JsonProperty(value = "id")
    //@NotEmpty
    private Long id;

    @JsonProperty (value = "ProfileImage")
    private File profileImage;

    public BasicDto () {

    }

    public BasicDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicDto basicDto = (BasicDto) o;
        return id.equals(basicDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
