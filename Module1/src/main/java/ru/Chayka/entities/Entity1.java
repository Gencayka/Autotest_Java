package ru.Chayka.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "table1", schema = "module1")
public final class Entity1
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

    @OneToOne
    @JoinColumn(name = "entity3_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity3 entity3;

    @OneToMany(
            mappedBy = "entity1",
            fetch = FetchType.LAZY)
    @OrderBy("date DESC")
    @Setter(value = AccessLevel.PRIVATE)
    private List<Entity2> entity2List;

    @Column(name = "number")
    private String number;

    @Column(name = "status")
    private String status;
}