package ru.Chayka.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter

@Entity
@Table(name = "table5", schema = "module1")
public final class Entity5
        implements Serializable {
    @Id
    @Column(name = "id")
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity3_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Entity3 entity3;

    @OneToOne(
            mappedBy = "entity5",
            fetch = FetchType.LAZY,
            optional = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Entity6 entity6;

    @Column(name = "field1")
    private String field1;

    @Column(name = "field2")
    private String field2;

    @Column(name = "field3")
    private String field3;

    @Column(name = "field4")
    private Timestamp field4;
}
