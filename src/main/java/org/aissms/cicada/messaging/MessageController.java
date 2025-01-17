package org.aissms.cicada.messaging;

import org.aissms.cicada.entity.ClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    
    @Autowired public SimpMessagingTemplate template;

    @MessageMapping("/send")
    public void sendMessage(Message<ClientMessage> message, OAuth2AuthenticationToken user) {
        ClientMessage ms = message.getPayload();
        String name = user.getPrincipal().getAttribute("login");
        if(name != null && name.equalsIgnoreCase(ms.getSender())) {
            template.convertAndSend("/messages/" + ms.getRecver(), ms);
        }
    }
}
