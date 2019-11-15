package com.scholanova.projectdb.controllers;

import com.scholanova.projectdb.exceptions.MessageCannotBeEmptyException;
import com.scholanova.projectdb.exceptions.MessageCannotBeOver50CharException;
import com.scholanova.projectdb.models.Message;
import com.scholanova.projectdb.services.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String listMessage(Model model) {
        List<Message> messages = messageService.listAll();

        model.addAttribute("messages", messages);
        return "message-list";
    }

    @GetMapping("/new")
    public String newMessage(Model model) {
        model.addAttribute("message", new Message(null, "", ""));
        return "message-new";
    }

    @PostMapping("/")
    public String createMessage(@ModelAttribute Message message, Model model) {
        try {
            Message newMessage = messageService.create(message);

            List<Message> messages = messageService.listAll();

            model.addAttribute("newMessage", newMessage);
            model.addAttribute("messages", messages);
            return "message-list";

        } catch (MessageCannotBeEmptyException messageCannotBeEmptyException) {
            model.addAttribute("message", message);
            model.addAttribute("errorMessage", "Message cannot be empty !");
            return "message-new";
        } catch (MessageCannotBeOver50CharException e) {
            model.addAttribute("message", message);
            model.addAttribute("errorMessage", "Message cannot be over 50 char !");
            return "message-new";

        }
    }
}
