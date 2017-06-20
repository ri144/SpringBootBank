package com.example.demo;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    //private Atm myAtm;

    @Autowired
    private ATMRepo atmRepo;

    @RequestMapping("/")
    public String login(Model model) {
        model.addAttribute("message", "Welcome, pick something to do.");
        return "options";
    }

    @GetMapping("/next")
    public String login2(Model model) {
        model.addAttribute("atm", new Atm());
        return "start";
    }

    @PostMapping("/next")
    public String base(@ModelAttribute Atm atm, Model model, BindingResult bindingResult) {
        String input = "Welcome user " + String.valueOf(atm.getcustId());
        model.addAttribute("message", input);
       // myAtm = atm;
        return "options";
    }

    @PostMapping("/depo")
    public String goDepo() {
        return "deposit";
    }

    @PostMapping("/with")
    public String goWith() {
        return "withdraw";
    }

    @PostMapping("/hist")
    public String goHist() {
        return "history";
    }

    @GetMapping("/depositForm")
    public String setupDeposit(Model model){
        model.addAttribute("atm", new Atm());
        return "deposit";
    }


    @PostMapping("/depositForm")
    public String makeDeposit(@ModelAttribute Atm atm, Model model, BindingResult bindingResult){
        atmRepo.save(atm);
        model.addAttribute("message", "Deposit successful");
        return "options";
    }

}