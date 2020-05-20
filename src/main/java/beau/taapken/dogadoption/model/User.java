package beau.taapken.dogadoption.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    @Id
    @NotNull
    @JsonProperty("UUID")
    @Column(unique = true)
    private String UUID;

    @NotNull
    @JsonProperty("Username")
    @Column(unique = true)
    private String Username;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonBackReference
    private List<Advert> adverts = new ArrayList<>();

    public User(String uuid, String username) {
        UUID = uuid;
        Username = username;
    }
}
