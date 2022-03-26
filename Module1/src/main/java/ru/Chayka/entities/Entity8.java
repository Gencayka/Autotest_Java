package ru.Chayka.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "table8", schema = "module1")
public final class Entity8
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

    @OneToMany(
            mappedBy = "entity3",
            fetch = FetchType.LAZY)
    @Setter(value = AccessLevel.PRIVATE)
    private List<Entity3> entity3List;

    @Column(name = "name")
    @Setter(value = AccessLevel.PRIVATE)
    private String name;
}
