package com.example.demo;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.context.SecurityContext;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.security.Principal;
        import java.util.Collection;
        import java.util.List;
        import java.util.Locale;

@Controller
public class HomeController {

    //private Atm myAtm;

    @Autowired
    private ATMRepo atmRepo;

    /*@Autowired
    ActiveUserStore activeUserStore;

    @RequestMapping(value = "/loggedUsers", method = RequestMethod.GET)
    public String getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }*/

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String login(Model model, Principal principal) {
        model.addAttribute("message", "Welcome, pick something to do.");
        model.addAttribute("accounts", atmRepo.findAcctByCustId(Long.valueOf(principal.getName())));
        model.addAttribute("holder", new Holder());
        return "options";
    }

    @GetMapping("/next")
    public String login2(Model model) {
        model.addAttribute("atm", new Atm());
        return "start";
    }

    @PostMapping("/next")
    public String base(@ModelAttribute Atm atm, Model model, Principal principal, BindingResult bindingResult) {
        String input = "Welcome user " + String.valueOf(atm.getCustId());
        model.addAttribute("message", input);
       // myAtm = atm;
        model.addAttribute("accounts", atmRepo.findAcctByCustId(Long.valueOf(principal.getName())));
        model.addAttribute("holder", new Holder());
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
    public String showHistory(@ModelAttribute Holder holder, Model model, Principal principal){
        List<Atm> values = atmRepo.findAllByAcctAndCustId(holder.getAcct(), Long.valueOf(principal.getName()));
        model.addAttribute("values", values);
        return "history";
    }

    @RequestMapping("/balance")
    public String showBalance(@ModelAttribute Holder holder, Model model, Principal principal){
        model.addAttribute("bal", String.valueOf(getBalance(Long.valueOf(principal.getName()), holder.getAcct())));
        return "balance";
    }

    @PostMapping("/depositForm")
    public String makeDeposit(@Valid Atm atm, Model model, Principal principal, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "deposit";
        }
        atm.setAction("deposit");
        atm.setCustId(Long.valueOf(principal.getName()));
        atmRepo.save(atm);
        model.addAttribute("message", "Deposit successful");
        model.addAttribute("accounts", atmRepo.findAcctByCustId(Long.valueOf(principal.getName())));
        model.addAttribute("holder", new Holder());
        return "options";
    }

    @PostMapping("/withdrawForm")
    public String makeWithdrawal(@Valid Atm atm, Model model, Principal principal, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "deposit";
        }
        atm.setAction("withdraw");
        atm.setAmount(atm.getAmount()*-1);
        atm.setCustId(Long.valueOf(principal.getName()));
        atmRepo.save(atm);
        model.addAttribute("message", "Withdrawal successful");
        model.addAttribute("accounts", atmRepo.findAcctByCustId(Long.valueOf(principal.getName())));
        model.addAttribute("holder", new Holder());
        return "options";
    }

    private double getBalance(long custId, int acct){
        List<Atm> amountList = atmRepo.findAllByAcctAndCustId(acct, custId);
        double sum = 0;
        for (Atm x : amountList) {
            sum += x.getAmount();
        }
        return sum;
    }

    private int getPassword(){
        SecurityContext securityContext = new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return null;
            }

            @Override
            public void setAuthentication(Authentication authentication) {

            }
        };
        Authentication authentication=securityContext.getAuthentication();
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        int pword = (Integer) auth.getCredentials();
        return pword;
    }

}