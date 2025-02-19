package pl.elpepe.foodorder.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);

    Optional<Item> findByNameIgnoreCase(String s);
}
