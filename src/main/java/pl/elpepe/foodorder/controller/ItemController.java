package pl.elpepe.foodorder.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.elpepe.foodorder.item.Item;
import pl.elpepe.foodorder.item.ItemRepository;

import java.util.Optional;

@Controller
public class ItemController {


    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @GetMapping("/dish/{itemName}")
    public String dish(@PathVariable String itemName, Model model) {
        Optional<Item> item = itemRepository.findByNameIgnoreCase(itemName.replaceAll("_", " "));
        item.ifPresent(it -> model.addAttribute("item", it));
        return item.map(it -> "item").orElse("redirect:/");
    }

}
