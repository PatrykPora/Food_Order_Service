package pl.elpepe.foodorder.order;


import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import pl.elpepe.foodorder.item.Item;
import pl.elpepe.foodorder.item.ItemRepository;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
@SessionScope
public class OrderService {


    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private  Order order = new Order();


    public Order getOrder() {
        return order;
    }

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public void addItemToOrder(Item item) {
        order.addItem(item);
    }

    public Optional<Item> findItemByName(String name) {
        return itemRepository.findByNameIgnoreCase(name.replaceAll("_", " "));
    }

    public Double totalPriceForOrder() {
        return order.getItems()
                .stream()
                .flatMapToDouble(item -> DoubleStream.of(item.getPrice()))
                .sum();
    }

    public void saveOrder(String address, String telephone) {
        order.setAddress(address);
        order.setPhone(telephone);
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);
        this.order = new Order();
    }

}
