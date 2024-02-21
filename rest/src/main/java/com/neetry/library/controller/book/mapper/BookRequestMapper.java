package com.neetry.library.controller.book.mapper;

import com.neetry.library.controller.book.model.CreateBookRequest;
import com.neetry.library.book.service.model.command.CreateBookCommand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)public interface BookRequestMapper {

    CreateBookCommand mapToCreateBookCommand(CreateBookRequest request);
}
