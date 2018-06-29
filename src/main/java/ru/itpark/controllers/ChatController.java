package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itpark.dto.ChatDto;
import ru.itpark.dto.MessageDto;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.MessageForm;
import ru.itpark.models.Chat;
import ru.itpark.models.Message;
import ru.itpark.models.Role;
import ru.itpark.models.User;
import ru.itpark.repositories.ChatRepository;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.service.ChatService;
import ru.itpark.service.RequestingService;
import ru.itpark.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private MessageDto messageDto;

    @Autowired
    private ChatDto chatDtoBean;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestingService requestingService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;


    @GetMapping("/profile/messages")
    public String getProfileMessage(ModelMap modelMap, Authentication authentication) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        if (user.getRole().equals(Role.ADMIN)) return "deleted";
        List<ChatDto> chatsDto = new ArrayList<>();
        if (userDto.getChats().size() != 0) {
            for (Chat chat : userDto.getChats()) {
                chatsDto.add(chatDtoBean.dtoChatFromChat(chat, user));
            }
        }
        if (chatsDto.size() != 0) modelMap.addAttribute("chats", chatsDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if (chatService.getCountNewMessages(user.getChats()) != 0)
            modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        return "dialogs";
    }

    @GetMapping("/chats/")
    public String getChatId(Authentication authentication, ModelMap modelMap, @RequestParam( value = "id") Long idChat) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        Chat chat = chatService.findChat(idChat).orElseThrow(IllegalArgumentException::new);
        modelMap.addAttribute("chat", chatDtoBean.dtoChatFromChat(chat, user));
//        modelMap.addAttribute("messages", messageDto.dtoMessagesFromMessages(chatService.getSortMessagesFromChat(chat).orElseThrow(IllegalArgumentException::new), userDto));
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if (chatService.getCountNewMessages(user.getChats()) != 0)
            modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        return "dialog";
    }

    @GetMapping("/chats/{id-chat}/messages/get")
    public ResponseEntity<Object> postMessagesGet(@PathVariable("id-chat") Long idChat, Authentication authentication) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        Chat chat = chatService.findChat(idChat).orElseThrow(IllegalArgumentException::new);
        Optional<List<Message>> messagesOptional = chatService.getSortMessagesFromChat(chat);
        if(messagesOptional.isPresent()) return ResponseEntity.ok(messageDto.dtoMessagesFromMessages(messagesOptional.get(), userDto));
        else return ResponseEntity.accepted().build();
    }

    @PostMapping("/chats/{id-chat}/messages/send")
    public ResponseEntity<Object> postMessagesSend(@PathVariable("id-chat") Long idChat, Authentication authentication,
                                                   @RequestBody MessageForm messageForm){
        chatService.saveAndDeliverMessage(authentication, idChat, messageForm);
        return ResponseEntity.accepted().build();
    }



    @Transactional
    @GetMapping("/users/{id-candidate}/message")
    public String getUsersIdMessage(@PathVariable("id-candidate") Long idCandidate, Authentication authentication, ModelMap modelMap) {
        User user =  userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOne(idCandidate);
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if(chatService.getCountNewMessages(user.getChats()) != 0 ) modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        Optional<Chat> chatOptional = chatService.isExist(authentication, idCandidate);
        Chat chat = null;
        if(chatOptional.isPresent()) return "redirect:/chats/?id=" + chatOptional.get().getId();
        else if(userRepositori.findOneById(idCandidate).isPresent() && userRepositori.findOneById(user.getId()).isPresent()){
            List<User> participants = Arrays.asList(user, candidate);
            chat = Chat.builder()
                    .name(user.getFirstName() + " " +  user.getLastName() + " / " + candidate.getFirstName() + " " + candidate.getLastName())
                    .messages(new ArrayList<Message>())
                    .participants(participants)
                    .build();
            chatRepository.save(chat);
        }
        if(chatService.findChat(user,candidate).isPresent()) {
            modelMap.addAttribute("chat", chatDtoBean.dtoChatFromChat(chatService.findChat(user,candidate).get(), user));
            return "dialog";
        }
        else return "redirect: /users/" + idCandidate;
    }
}
