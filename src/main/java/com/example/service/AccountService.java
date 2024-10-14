package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class AccountService {

    private AccountRepository ar;
    @Autowired
    public AccountService(AccountRepository ar){
        this.ar =ar;
    }
    
    public int UserRegestration(String username, String password){
        if(username!=null && username.trim().length()!=0 && (password!=null && password.length()>3)){
            if(!ar.existsByUsername(username)){
                Account account = new Account(username,password);
                ar.save(account);
                return account.getAccountId();//fixed
            }
            else
                return -2;// d
        }
            return -1;//l
    }

    public int Login(String username, String password){
        Optional<Account> x = ar.findByUsername(username);
        if(x.isPresent()){
           Account a = x.get();
           if(password.equals(a.getPassword()))
            return a.getAccountId();
        }
        return -1;
    }
}
