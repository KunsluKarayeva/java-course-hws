package hw1;

import java.util.List;
import java.util.Optional;

public interface CoworkingRepository {
    List<CoworkingSpace> findAll();

    Optional<CoworkingSpace> findById(int id);

    void save(CoworkingSpace space);

    void deleteById(int id);
}