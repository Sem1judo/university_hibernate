package com.ua.foxminded.university.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Table(name="lectors")
public class Lector {

    @Id
    @Column(name="lector_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long lectorId;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @NotBlank
    @Size(min = 3, max = 50,
            message = "First name must be between 3 and 20 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "First name must be alphanumeric with no spaces")
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50,
            message = "Last name must be between 3 and 20 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Last name must be alphanumeric with no spaces")
    @Column(name="last_name")
    private String lastName;

    public Lector() {
    }

    public Lector(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Lector(Long lectorId, String firstName, String lastName) {
        this.lectorId = lectorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Lector(long lectorId, Faculty faculty, @NotBlank @Size(min = 3, max = 50,
            message = "First name must be between 3 and 20 characters long") @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "First name must be alphanumeric with no spaces") String firstName, @NotBlank @Size(min = 3, max = 50,
            message = "Last name must be between 3 and 20 characters long") @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Last name must be alphanumeric with no spaces") String lastName) {
        this.lectorId = lectorId;
        this.faculty = faculty;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getLectorId() {
        return lectorId;
    }

    public void setLectorId(Long lectorId) {
        this.lectorId = lectorId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lector lector = (Lector) o;
        return lectorId == lector.lectorId &&
                Objects.equals(faculty, lector.faculty) &&
                Objects.equals(firstName, lector.firstName) &&
                Objects.equals(lastName, lector.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectorId, faculty, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Lector{" +
                "lectorId=" + lectorId +
                ", faculty=" + faculty +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
