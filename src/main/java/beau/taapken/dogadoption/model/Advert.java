package beau.taapken.dogadoption.model;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.projection.IGetAdvert;
import beau.taapken.dogadoption.projection.IAdvertFeed;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "advert")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Advert implements IGetAdvert, IAdvertFeed {
    @Id
    @JsonProperty("advertId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(unique = true)
    private String advertId;

    @NotNull
    @JsonProperty("UUID")
    @ManyToOne
    private User user;

    @NotNull
    @JsonProperty("img")
    @Column(length = 999999999)
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

    @NotNull
    @JsonProperty("longtitude")
    private float longtitude;

    @NotNull
    @JsonProperty("latitude")
    private float latitude;

    @NotNull
    @JsonProperty("place")
    private String place;

    @JsonProperty("date")
    private LocalDateTime dateTime;
}
