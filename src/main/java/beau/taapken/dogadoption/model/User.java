package beau.taapken.dogadoption.model;

import beau.taapken.dogadoption.embeddable.UserId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
}
