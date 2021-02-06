package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;


@Getter
@Setter
public class BulkImageWrapper {
    @NotEmpty(message = "Image list cannot be empty!!")
    LinkedHashSet<ImageWrapper> imageWrapperSet = new LinkedHashSet<>();
}
