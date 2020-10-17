package com.ua.foxminded.university.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="lessons")
public class Lesson {

    @Id
    @Column(name="lesson_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long lessonId;

    @NotBlank
    @Size(min = 3, max = 50,
            message = "Lesson name must be between 3 and 20 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Lesson name must be alphanumeric with no spaces")
    @Column(name="lesson_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "lector_id")
    private Lector lector;

    public Lesson() {
    }

    public Lesson(long lessonId, @NotBlank @Size(min = 3, max = 50,
            message = "Lesson name must be between 3 and 20 characters long") @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Lesson name must be alphanumeric with no spaces") String name) {
        this.lessonId = lessonId;
        this.name = name;
    }

    public Lesson(long lessonId, @NotBlank @Size(min = 3, max = 50,
            message = "Lesson name must be between 3 and 20 characters long") @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Lesson name must be alphanumeric with no spaces") String name, Lector lector) {
        this.lessonId = lessonId;
        this.name = name;
        this.lector = lector;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return lessonId == lesson.lessonId &&
                Objects.equals(name, lesson.name) &&
                Objects.equals(lector, lesson.lector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, name, lector);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", name='" + name + '\'' +
                ", lector=" + lector +
                '}';
    }
}
