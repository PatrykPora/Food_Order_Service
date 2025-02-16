package pl.elpepe.foodorder.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Object findByName(String name);
}
