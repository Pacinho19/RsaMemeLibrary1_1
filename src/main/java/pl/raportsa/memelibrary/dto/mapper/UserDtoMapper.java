package pl.raportsa.memelibrary.dto.mapper;

import pl.raportsa.memelibrary.dto.UserDto;
import pl.raportsa.memelibrary.entity.User;

public class UserDtoMapper {

    public static User parse(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword());
    }
}
