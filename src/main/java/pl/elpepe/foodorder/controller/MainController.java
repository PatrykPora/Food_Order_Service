package pl.elpepe.foodorder.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.elpepe.foodorder.item.ItemRepository;

@Controller
public class MainController {

    private final ItemRepository itemRepository;

    public MainController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "index";
    }


}
