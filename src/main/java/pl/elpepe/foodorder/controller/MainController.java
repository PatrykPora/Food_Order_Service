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

    @GetMapping("/dish/Pizza_Margherita")
    public String pizzaMargherita(Model model) {
    model.addAttribute("margherita", itemRepository.findByName("Pizza Margherita"));
    return "pizzaMargherita";
    }

    @GetMapping("/dish/Pizza_Capriciosa")
    public String pizzaCapriciosa(Model model) {
        model.addAttribute("capriciosa", itemRepository.findByName("Pizza Capriciosa"));
        return "pizzaCapriciosa";
    }

    @GetMapping("/dish/Spaghetti_Bolognese")
    public String spagethiBolognese(Model model) {
        model.addAttribute("bolognese", itemRepository.findByName("Spaghetti Bolognese"));
        return "spaghettiBolognese";
    }


    @GetMapping("/dish/Panna_Cotta")
    public String pannaCotta(Model model) {
        model.addAttribute("panaCotta", itemRepository.findByName("Panna Cotta"));
        return "pannaCotta";
    }




}
