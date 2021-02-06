package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AssignManager {

    @NotNull(message = "Manager id cannot be null!")
    private Integer employeeId;

    @NotNull(message = "Manager id cannot be null!")
    private Integer managerId;
}
