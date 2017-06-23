package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by student on 6/20/17.
 */
public interface ATMRepo extends CrudRepository<Atm, Long> {
    public List<Double> findAmountByCustId(Long custid);

    public List<String> findActionByAcctAndCustId(Integer acct, Long custid);

    public List<Double> findAmountByAcctAndCustId(Integer acct, Long custid);

    public List<Atm> findAllByAcctAndCustId(Integer acct, Long custid);

    public List<Integer> findAcctByCustId(Long custid);

    public List<Atm> findAllByCustId(Long custid);

    public List<Atm> findDistinctByCustId(Long custid);
}
