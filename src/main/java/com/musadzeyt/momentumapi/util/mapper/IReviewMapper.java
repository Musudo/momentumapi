package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IReviewMapper {
    IReviewMapper INSTANCE = Mappers.getMapper(IReviewMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "activityId", source = "activity.id")
    ReviewDto entityToDto(Review review);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Review dtoToEntity(ReviewDto reviewDto);

    List<ReviewDto> entityListToDtoList(List<Review> list);

    List<Review> dtoListToEntityList(List<ReviewDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review update(ReviewDto reviewDto, @MappingTarget Review review);
}