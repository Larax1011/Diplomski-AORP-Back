package raf.lazar.diplomski_aorp.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import raf.lazar.diplomski_aorp.requests.PredavacCreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Predavac {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String type;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Predavanje> predavanja = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VanredniCas> vanredniCasovi = new ArrayList<>();

    public Predavac(String name, String lastname, String email, String type) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.type = type;
    }

    public Predavac(PredavacCreateRequest request) {
        this.name = request.getName();
        this.lastname = request.getLastname();
        this.email = request.getEmail();
        this.type = request.getType();
    }

    public Predavac() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Predavac predavac = (Predavac) o;
        return Objects.equals(name, predavac.name) && Objects.equals(lastname, predavac.lastname) && Objects.equals(email, predavac.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastname, email);
    }

    @Override
    public String toString() {
        return "Predavac{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
