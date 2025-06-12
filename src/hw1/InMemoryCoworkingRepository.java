package hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCoworkingRepository implements CoworkingRepository {
    private final List<CoworkingSpace> store = new ArrayList();

    public List<CoworkingSpace> findAll() {
        return new ArrayList(this.store);
    }

    public Optional<CoworkingSpace> findById(int id) {
        return this.store.stream().filter((s) -> s.getId() == id).findFirst();
    }

    public void save(CoworkingSpace space) {
        this.deleteById(space.getId());
        this.store.add(space);
    }

    public void deleteById(int id) {
        this.store.removeIf((s) -> s.getId() == id);
    }
}