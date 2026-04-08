package com.naverrain.grocery.persistence.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    @Size(max = 64)
    @ToString.Include
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_role_privileges_role_privilege",
                    columnNames = {"role_id", "privilege_id"}
            )
    )
    private Set<Privilege> privileges = new HashSet<>();

    public void addPrivilege(Privilege privilege) {
        if (privilege != null) {
            privileges.add(privilege);
        }
    }

    public void removePrivilege(Privilege privilege) {
        if (privilege != null) {
            privileges.remove(privilege);
        }
    }
}
