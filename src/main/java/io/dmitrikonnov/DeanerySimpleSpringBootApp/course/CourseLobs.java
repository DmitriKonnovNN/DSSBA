package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class CourseLobs {

    @Id
    Long id;
    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId
    CourseEntity courseEntity;

    @Lob
    private byte [] profileImage;

    public CourseLobs(CourseEntity courseEntity, byte[] profileImage) {
        this.courseEntity = courseEntity;
        this.profileImage = profileImage;
    }
}
