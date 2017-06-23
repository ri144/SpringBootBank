package com.example.demo;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.util.List;

@Controller
public class HomeController {

    //private Atm myAtm;

    @Autowired
    private ATMRepo atmRepo;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

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
        String input = "Welcome user " + String.valueOf(atm.getCustId());
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

    @RequestMapping("/deposit")
    public String setupDeposit(Model model){
        model.addAttribute("atm", new Atm());
        return "deposit";
    }

    @RequestMapping("/withdraw")
    public String setupWithdraw(Model model){
        model.addAttribute("atm", new Atm());
        return "withdraw";
    }

    @RequestMapping("/history")
    public String showHistory(Model model){
        List<Atm> values = atmRepo.findAllByAcctAndCustId(123, 1l);
        model.addAttribute("values", values);
        return "history";
    }

    @RequestMapping("/balance")
    public String showBalance(Model model){
        model.addAttribute("bal", String.valueOf(getBalance(1l, 123)));
        return "balance";
    }

    @PostMapping("/depositForm")
    public String makeDeposit(@Valid Atm atm, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "deposit";
        }
        atm.setAction("deposit");
        atmRepo.save(atm);
        model.addAttribute("message", "Deposit successful");
        return "options";
    }

    @PostMapping("/withdrawForm")
    public String makeWithdrawal(@Valid Atm atm, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "deposit";
        }
        atm.setAction("withdraw");
        atm.setAmount(atm.getAmount()*-1);
        atmRepo.save(atm);
        model.addAttribute("message", "Withdrawal successful");
        return "options";
    }

    public double getBalance(long custId, int acct){
        List<Double> amountList = atmRepo.findAmountByAcctAndCustId(acct, custId);
        double sum = 0;
        for (Double x : amountList) {
            sum += x;
        }
        return sum;
    }

}