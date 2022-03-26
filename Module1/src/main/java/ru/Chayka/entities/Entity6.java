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
@Table(name = "table6", schema = "module1")
public final class Entity6
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

    @OneToOne
    @JoinColumn(name = "entity5_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity5 entity5;

    @Column(name = "field1")
    private String field1;

    @Column(name = "field2")
    private String field2;

    @Column(name = "field3")
    private String field3;

    @Column(name = "field4")
    private Timestamp field4;
}
