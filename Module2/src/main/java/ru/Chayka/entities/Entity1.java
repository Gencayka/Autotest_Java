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
@Table(name = "table1", schema = "module2")
public final class Entity1
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private Integer id;

    @OneToMany(
            mappedBy = "entity1",
            fetch = FetchType.LAZY)
    @OrderBy("date DESC")
    @Setter(value = AccessLevel.PRIVATE)
    private List<Entity2> portfolioList;

    @Column(name = "version")
    private String version;
}
