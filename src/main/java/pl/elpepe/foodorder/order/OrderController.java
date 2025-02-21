package pl.elpepe.foodorder.order;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.elpepe.foodorder.item.Item;

import java.util.Optional;


@Controller
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/addItem/{itemId}")
    public ResponseEntity<String> addItemToOrder(@PathVariable Long itemId) {
        Optional<Item> item = orderService.findItemById(itemId);
        if (item.isPresent()) {
            orderService.addItemToOrder(item.get());
            return ResponseEntity.ok("Item added to order");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
    }


    @GetMapping("/order")
    public String orderForm(Model model) {
        model.addAttribute("order", orderService.getOrder());
        model.addAttribute("totalPrice", orderService.totalPriceForOrder());
        return "orderForm";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam String address, @RequestParam String telephone) {
        orderService.saveOrder(address, telephone);
        return "orderConfirmation";
    }

}
