package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.ReviewEmail;
import com.musadzeyt.momentumapi.dto.ReviewEmailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IReviewEmailMapper {
    IReviewEmailMapper INSTANCE = Mappers.getMapper(IReviewEmailMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "reviewId", source = "review.id")
    ReviewEmailDto entityToDto(ReviewEmail reviewEmail);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    ReviewEmail dtoToEntity(ReviewEmailDto reviewEmailDto);

    List<ReviewEmailDto> entityListToDtoList(List<ReviewEmail> list);

    List<ReviewEmail> dtoListToEntityList(List<ReviewEmailDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ReviewEmail update(ReviewEmailDto reviewEmailDto, @MappingTarget ReviewEmail reviewEmail);
}