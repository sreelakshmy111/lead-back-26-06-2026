package com.project.Permission.of.lead.mapper;
import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.entity.Users;

public class UserMapper {

    public static UserDto mapToUserDto(Users users) {

        return new UserDto(
               users.getUser_id(),
               users.getUsername(),
               users.getPassword(),
                users.getEmail(),
                users.getAddressId()

//                users.getRole() != null ? users.getRole().getId() : null,  // roleId
//                users.getRoleName()


        );
    }

    public static Users mapToUsers(UserDto userDto) {

        return new Users(
                userDto.getUser_id(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getAddressId()!=null? userDto.getAddressId():null

        );
    }





}
