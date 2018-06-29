package ru.itpark.dto;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String dataBirthdayConvert;
    private LocalDate dataBirthday;
    private String city;
    private String avatarUrl;
    private List<User> friends;
    private List<User> friendOf;
    private List<Requesting> outputRequestings;
    private List<Requesting> inputRequestings;
    private List<Post> posts;
    private List<Chat> chats;
    private String status;
    private String role;
    private String condition;

    private static String converterDate(String date){
        String [] arrStr = date.split("-");
        String dateTemp = "";
        HashMap<String, String> map = new HashMap<>();
        map.put("01", "января");
        map.put("02", "февраля");
        map.put("03", "марта");
        map.put("04", "апреля");
        map.put("05", "мая");
        map.put("06", "июня");
        map.put("07", "июля");
        map.put("08", "августа");
        map.put("09", "сентября");
        map.put("10", "октября");
        map.put("11", "ноября");
        map.put("12", "декабря");

        for(int i = 2; i >= 0; i--){
            if(i == 2) dateTemp += (arrStr[i] + " ");
            else if(i == 1) dateTemp += (map.get(arrStr[i]) + " ");
            else dateTemp += (arrStr[i] + " г.");
        }
        return dateTemp;
    }

    public static UserDto dtoUserFromUser(User user){
        String avatarUrlUser = null;
        if(user.getAvatarFileInfo() != null) avatarUrlUser = "/files/" + user.getAvatarFileInfo().getStorageName();
        else avatarUrlUser = "/img/no_avatar.jpg";
        if(user.getRole().equals(Role.ADMIN)) avatarUrlUser = "/img/deleted.jpg";

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dataBirthdayConvert(converterDate(user.getDataBirthday().toString()))
                .dataBirthday(user.getDataBirthday())
                .city(user.getCity())
                .avatarUrl(avatarUrlUser)
                .friends(user.getFriends())
                .friendOf(user.getFriendOf())
                .outputRequestings(user.getOutputRequestings())
                .inputRequestings(user.getInputRequestings())
                .posts(user.getPosts())
                .chats(user.getChats())
                .status(null)
                .role(user.getRole().name())
                .condition(null)
                .build();
    }
}
