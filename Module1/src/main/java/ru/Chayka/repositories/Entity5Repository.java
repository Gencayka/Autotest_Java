package ru.Chayka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Chayka.entities.Entity5;

import java.util.List;

@Repository
public interface Entity5Repository extends JpaRepository<Entity5, String> {
    List<Entity5> findByField3(String field3);
}
