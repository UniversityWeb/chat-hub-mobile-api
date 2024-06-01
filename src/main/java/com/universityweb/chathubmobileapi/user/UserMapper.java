package com.universityweb.chathubmobileapi.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Chuyển đổi một đối tượng User thành một đối tượng UserDTO.
     *
     * @param user  :   Đối tượng User cần chuyển đổi.
     * @return      :   Đối tượng UserDTO đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO toDTO(User user);

    /**
     * Chuyển đổi một danh sách các đối tượng User thành một danh sách các đối tượng UserDTO.
     *
     * @param users :   Danh sách các đối tượng User cần chuyển đổi.
     * @return              :   Danh sách các đối tượng UserDTO đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    List<UserDTO> toDTOs(List<User> users);

    /**
     * Chuyển đổi một đối tượng UserDTO thành một đối tượng User.
     *
     * @param userDTO  :   Đối tượng UserDTO cần chuyển đổi.
     * @return      :   Đối tượng User đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    User toEntity(UserDTO userDTO);

    /**
     * Chuyển đổi một danh sách các đối tượng UserDTO thành một danh sách các đối tượng User.
     *
     * @param userDTOs :   Danh sách các đối tượng UserDTO cần chuyển đổi.
     * @return              :   Danh sách các đối tượng User đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    List<User> toEntities(List<UserDTO> userDTOs);
}
