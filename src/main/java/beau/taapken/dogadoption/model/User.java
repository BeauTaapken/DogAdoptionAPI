package beau.taapken.dogadoption.model;

import beau.taapken.dogadoption.embeddable.UserId;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "user")
@IdClass(UserId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    @Id
    @NotNull
    private String UUID;

    @Id
    @NotNull
    private String Username;
}
