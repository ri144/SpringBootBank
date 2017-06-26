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
        import java.text.DecimalFormat;
        import java.text.NumberFormat;
        import java.util.ArrayList;
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
        String input = "Welcome user " + principal.getName();
        List<Integer> myList = getAllAccounts(principal);
        model.addAttribute("accounts", myList);
        model.addAttribute("message", input);
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
        List<Integer> myList = getAllAccounts(principal);
        model.addAttribute("accounts", myList);
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
    public String setupDeposit(Model model, Principal principal){
        model.addAttribute("atm", new Atm());
        List<Integer> myList = getAllAccounts(principal);
        model.addAttribute("accounts", myList);
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

    @RequestMapping("/history/{id}")
    public String showHistory2(@PathVariable ("id") String id, Model model, Principal principal){
        List<Atm> values = atmRepo.findAllByAcctAndCustId(Integer.valueOf(id), Long.valueOf(principal.getName()));
        model.addAttribute("values", values);
        return "history";
    }

    @RequestMapping("/balance")
    public String showBalance(@ModelAttribute Holder holder, Model model, Principal principal){
        model.addAttribute("bal", String.valueOf(getBalance(Long.valueOf(principal.getName()), holder.getAcct())));
        return "balance";
    }

    @RequestMapping("/balance/{id}")
    public String showBalance2(@PathVariable ("id") String id, Model model, Principal principal){
        model.addAttribute("bal", String.valueOf(getBalance(Long.valueOf(principal.getName()), Integer.valueOf(id))));
        return "balance";
    }

    @PostMapping("/depositForm")
    public String makeDeposit(@Valid Atm atm, Model model, Principal principal, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "deposit";
        }
        atm.setAction("deposit");
       // NumberFormat formatter = new DecimalFormat("#0.00");
        //atm.setAmount(Double.valueOf(formatter.format(String.valueOf(atm.getAmount()))));
        atm.setCustId(Long.valueOf(principal.getName()));
        atmRepo.save(atm);
        List<Integer> myList = getAllAccounts(principal);
        model.addAttribute("message", "Deposit successful");
        model.addAttribute("accounts", myList);
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
        //NumberFormat formatter = new DecimalFormat("#0.00");
        //atm.setAmount(Double.valueOf(formatter.format(String.valueOf(atm.getAmount()))));
        atm.setCustId(Long.valueOf(principal.getName()));
        atmRepo.save(atm);
        List<Integer> myList = getAllAccounts(principal);
        model.addAttribute("message", "Withdrawal successful");
        model.addAttribute("accounts", myList);
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

    private List<Integer> getAllAccounts(Principal principal){
        List<Atm> list = atmRepo.findDistinctByCustId(Long.valueOf(principal.getName()));
        List<Integer> myList =  new ArrayList<Integer>();
        for(Atm a : list){
            if(!myList.contains(a.getAcct())) {
                myList.add(a.getAcct());
            }
        }
        return myList;
    }

}