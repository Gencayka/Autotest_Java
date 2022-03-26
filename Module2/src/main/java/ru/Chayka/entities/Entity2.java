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
@Table(name = "table2", schema = "module2")
public final class Entity2
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity1_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity1 entity1;

    @Column(name = "version")
    private Integer version;

    @Column(name = "data")
    private String data;

    @Column(name = "date")
    private Timestamp date;
}
