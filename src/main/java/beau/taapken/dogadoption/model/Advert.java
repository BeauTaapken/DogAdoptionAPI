package beau.taapken.dogadoption.model;

import beau.taapken.dogadoption.enumerator.DogBreed;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "advert")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Advert  {
    @Id
    @JsonProperty("advertId")
    @GeneratedValue
    @Column(unique = true)
    private int advertId;

    @NotNull
    @JsonProperty("UUID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User UUID;

    @NotNull
    @JsonProperty("img")
    private String image;

    @NotNull
    @JsonProperty("title")
    private String title;

    @NotNull
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("breed")
    private DogBreed breed;

    @NotNull
    @JsonProperty("age")
    private int age;
}
