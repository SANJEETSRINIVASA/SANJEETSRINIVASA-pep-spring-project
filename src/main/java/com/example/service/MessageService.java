package com.example.service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private MessageRepository mr;
    @Autowired
    public MessageService(MessageRepository mr){
        this.mr = mr;
    }

    public int CreateNewMessage(int postedBy, String message_text, long timePostedEpoch){
        if(message_text!=null && message_text.trim().length()!=0 && message_text.length()<=255){
            if(mr.existsByPostedBy(postedBy)){
                Message message = new Message(postedBy, message_text, timePostedEpoch);
                mr.save(message);
                return message.getMessageId(); 
            }
            else
                return -1;
        }
        return -1;
    }

    public List<Message> GetAllMessages(){
       return mr.findAll();
    }

    public Message GetOneMessage(int messageId){
        Optional<Message> x = mr.findById(messageId);
        if(x.isPresent()){
            return x.get();
        }
        return null;
    }

    public int DeleteAMessage(int messageId){
        if(mr.existsById(messageId)){
            mr.deleteById(messageId);
            return 1;
        }
        else
            return 0;
    }

    public int UpdateAMessage(int messageId, String message_text){
        if(message_text!=null && message_text.trim().length()!=0 && message_text.length()<=255 && mr.existsById(messageId)){
            Optional<Message> x = mr.findById(messageId);
            if(x.isPresent()){
                Message m = x.get();
                m.setMessageText(message_text);
                mr.save(m);
                return 1;
            }
            else
                return 0;
        }
        else
            return 0;
    }

    public List<Message> GetAllMessages(int accountId){
        return mr.findAllByPostedBy(accountId);
    }

}
