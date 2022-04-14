package ru.Chayka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Chayka.entities.Entity3;
import ru.Chayka.TestClient;

import java.sql.Date;
import java.util.List;

@Repository
public interface Entity3Repository extends JpaRepository<Entity3, String> {
    List<Entity3> findByClient(@Param("argFirstName") String firstName,
                               @Param("argLastName") String lastName,
                               @Param("argMiddleName") String middleName,
                               @Param("argBirthDay") Date birthDay,
                               @Param("argIdocTypeId") String documentTypeId,
                               @Param("argIdocSeries") String series,
                               @Param("argIdocNumber") String number);

    default List<Entity3> findByClient(TestClient client){
        return findByClient(
                client.getFirstName(),
                client.getLastName(),
                client.getMiddleName(),
                client.getBirthDateAsDate(),
                "21",
                client.getIdentityCardSeries(),
                client.getIdentityCardNumber());
    }
}
