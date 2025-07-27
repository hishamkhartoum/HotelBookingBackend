package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.RegistrationRequest;
import com.hadef.hotelbooking.domain.dto.UserDto;
import com.hadef.hotelbooking.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);
    User fromRegistrationRequestToEntity(RegistrationRequest registrationRequest);
}
