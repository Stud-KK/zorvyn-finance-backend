package com.zorvyn.finance.dto.user;

import com.zorvyn.finance.entity.Role;
import com.zorvyn.finance.entity.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private Status status;
}