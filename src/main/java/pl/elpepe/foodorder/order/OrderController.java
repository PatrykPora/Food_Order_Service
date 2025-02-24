package pl.elpepe.foodorder.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.elpepe.foodorder.common.Message;
import pl.elpepe.foodorder.item.Item;
import pl.elpepe.foodorder.item.ItemRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {


    private ClientOrder clientOrder;
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;

    public OrderController(ClientOrder clientOrder, ItemRepository itemRepository, OrderRepository orderRepository) {
        this.clientOrder = clientOrder;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }


    @GetMapping("/order/addItem")
    public String addItemToOrder(@RequestParam Long itemId, Model model) {
        Optional<Item> item = itemRepository.findById(itemId);
        item.ifPresent(clientOrder::add);
        if (item.isPresent()) {
            model.addAttribute("message", new Message("Added", "Added to order: " + item.get().getName()));
        } else {
            model.addAttribute("message", new Message("Not added", "wrong id " + itemId));
        }
        return "message";
    }


    @GetMapping("/order")
    public String getCurrentOrder(Model model) {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", clientOrder
                .getOrder()
                .getItems()
                .stream()
                .mapToDouble(Item::getPrice)
                .sum());
        return "order";
    }

    @PostMapping("/order/submit")
    public String submitOrder(@RequestParam String address, @RequestParam String telephone, Model model) {
        Order order = clientOrder.getOrder();
        order.setAddress(address);
        order.setPhone(telephone);
        orderRepository.save(order);
        clientOrder.clear();
        model.addAttribute("message", new Message("Thank You", "your order is being processed"));
        return "message";
    }


    @GetMapping("/panel/orders")
    public String getOrdersFiltered(@RequestParam(value = "status", required = false) OrderStatus status, Model model) {
        List<Order> orders;
        if (status != null) {
            orders = orderRepository.findByStatus(status);
        } else {
            orders = orderRepository.findAll();
        }
        model.addAttribute("orders", orders);
        model.addAttribute("status", status);
        return "orders-panel";
    }


    @GetMapping("/panel/orders/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            model.addAttribute("sum", order.get().getItems()
                    .stream()
                    .mapToDouble(Item::getPrice)
                    .sum());
        } else {
            model.addAttribute("message", new Message("Not found", "could not find order with id: " + id));
        }
        return "order-edition";
    }


    @GetMapping("/order/process")
    public String processOrder(@RequestParam Long orderId, Model model) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            if (order.get().getStatus() == OrderStatus.NEW) {
                order.get().setStatus(OrderStatus.IN_PROGRESS);
                orderRepository.save(order.get());
                model.addAttribute("message", new Message("Status changed", "You have updated order status"));
            } else if (order.get().getStatus() == OrderStatus.IN_PROGRESS) {
                order.get().setStatus(OrderStatus.COMPLETED);
                orderRepository.save(order.get());
                model.addAttribute("message", new Message("Status changed", "You have updated order status"));
            } else {
                model.addAttribute("message", new Message("Status unchanged", "status has not been changed"));
            }
        } else {
            model.addAttribute("message", new Message("Status unchanged", "order with provided id not found or error occured"));
        }
        return "message";
    }

}
