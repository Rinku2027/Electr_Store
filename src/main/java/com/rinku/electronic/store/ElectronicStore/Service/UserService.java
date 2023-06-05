package com.rinku.electronic.store.ElectronicStore.Service;

import com.rinku.electronic.store.ElectronicStore.Dtos.UserDto;

import java.util.List;

public interface UserService {
UserDto createUser(UserDto userDto);
UserDto updateUser(UserDto userDto,String userId);
void deleteUser(String userId);
UserDto getUserById(String userId);
UserDto getUserByEmail(String email);
List<UserDto> getAllUser();
List<UserDto> searchUser(String keywords);
}
