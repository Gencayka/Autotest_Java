package ru.Chayka.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter

@Entity
@Table(name = "table7", schema = "module1")
public final class Entity7
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

    @OneToOne
    @JoinColumn(name = "entity3_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity3 entity3;

    @Column(name = "field1")
    @Setter(value = AccessLevel.PUBLIC)
    private String field1;

    @Column(name = "field2")
    @Setter(value = AccessLevel.PRIVATE)
    private String field2;

    @Column(name = "field3")
    @Setter(value = AccessLevel.PRIVATE)
    private Integer field3;

    @Column(name = "field4")
    @Setter(value = AccessLevel.PRIVATE)
    private String field4;

    @Column(name = "field5")
    private Timestamp field5;

    @Column(name = "field6")
    private String field6;
}
