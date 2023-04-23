package pt.aveiro.nariz.fullfledgedrp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_authorities")
@Data
@NoArgsConstructor
public class UserAuthorityEntity {
    @Id
    @GeneratedValue
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_fk"))
    @ToString.Exclude
    UserEntity user;
    @ManyToOne
    @JoinColumn(name = "authority_id", foreignKey = @ForeignKey(name = "authority_fk"))
    @ToString.Exclude
    AuthorityEntity authority;
}
