package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService as;
    @Autowired
    MessageService ms;
    int x;
    @PostMapping("/register")
    public ResponseEntity UserRegestration(@RequestBody Account a){
        x = as.UserRegestration(a.getUsername(),a.getPassword());
        if(x==-2)
            return ResponseEntity.status(409).body(null);
        if(x==-1)
            return ResponseEntity.status(400).body(null);
        else{
            a.setAccountId(x);
            return ResponseEntity.status(200).body(a);
        }
    }

    @PostMapping("/login")
    public ResponseEntity Login(@RequestBody Account a){
        x = as.Login(a.getUsername(), a.getPassword());
        if(x!=-1){
            a.setAccountId(x);
            return ResponseEntity.status(200).body(a);
        }
        return ResponseEntity.status(401).body(null); 
    }

    @PostMapping("/messages")
    public ResponseEntity CreateNewMessage(@RequestBody Message m){
        x = ms.CreateNewMessage(m.getPostedBy(), m.getMessageText(), m.getTimePostedEpoch());
        if(x!=-1){
            m.setMessageId(x);
            return ResponseEntity.status(200).body(m);
        }
        return ResponseEntity.status(400).body(null); 
    }

    @GetMapping("/messages")
    public ResponseEntity GetAllMessages(){
        return ResponseEntity.status(200).body(ms.GetAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity GetOneMessage(@PathVariable int message_id){
        return ResponseEntity.status(200).body(ms.GetOneMessage(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity DeleteAMessage(@PathVariable int message_id){
        x = ms.DeleteAMessage(message_id);
        if(x==1)
            return ResponseEntity.status(200).body(x);
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity UpdateAMessage(@PathVariable int message_id, @RequestBody Message m){
        x = ms.UpdateAMessage(message_id,m.getMessageText());
        if(x==1)
            return ResponseEntity.status(200).body(x);
        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity GetAllMessages(@PathVariable int account_id){
        return ResponseEntity.status(200).body(ms.GetAllMessages(account_id));
    }
}
