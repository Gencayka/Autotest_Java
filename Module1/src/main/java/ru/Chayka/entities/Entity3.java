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
@Table(name = "table3", schema = "module1")
public final class Entity3
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

    @OneToOne(mappedBy = "entity3")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity1 entity1;

    @OneToOne(
            mappedBy = "entity3",
            fetch = FetchType.LAZY,
            optional = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Entity5 entity5;

    @OneToOne(
            mappedBy = "entity3",
            fetch = FetchType.LAZY,
            optional = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Entity7 entity7;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "entity3",
            fetch = FetchType.LAZY)
    @OrderBy("field3 DESC")
    @Setter(value = AccessLevel.PRIVATE)
    private List<Entity4> entity4List;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity8_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity8 strategy;

    @Column(name = "field1")
    private String field1;

    @Column(name = "field2")
    private String field2;

    @Column(name = "field3")
    private String field3;

    @Column(name = "field4")
    private Timestamp field4;
}
