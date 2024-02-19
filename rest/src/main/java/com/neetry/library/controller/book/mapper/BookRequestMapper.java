package com.neetry.library.controller.book.mapper;

import com.neetry.library.controller.book.model.CreateBookRequest;
import com.neetry.library.book.service.model.command.CreateBookCommand;
import org.mapstruct.Mapper;

@Mapper
public interface BookRequestMapper {

    CreateBookCommand mapToCreateBookCommand(CreateBookRequest request);
}
