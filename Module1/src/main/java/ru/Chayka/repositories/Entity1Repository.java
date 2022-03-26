package ru.Chayka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Chayka.entities.Entity1;

import java.util.List;

@Repository
public interface Entity1Repository extends JpaRepository<Entity1, String> {
    Entity1 findByNumber(String number);
    List<Entity1> findByStatus(String status);
}
