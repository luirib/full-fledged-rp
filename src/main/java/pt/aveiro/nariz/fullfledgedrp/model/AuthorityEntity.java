package pt.aveiro.nariz.fullfledgedrp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.aveiro.nariz.fullfledgedrp.security.LoginProvider;

@Entity
@Table(name = "authority_entities")
@Data
@NoArgsConstructor
public class AuthorityEntity {
    @Id
    @GeneratedValue
    long id;
    String name;

    @Enumerated(EnumType.STRING)
    LoginProvider provider;

    @OneToMany(mappedBy = "authority")
    List<UserAuthorityEntity> assignedToUsers = new ArrayList<>();
}
