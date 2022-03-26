package ru.Chayka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Chayka.entities.Entity1;

@Repository
public interface Entity1Repository extends JpaRepository<Entity1, String> {
    Entity1 findByVersion(String version);
}
