package com.example.test.domain.mapper;

import com.example.test.domain.entity.TestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.example.test.domain.dto.Test;

@Mapper
public interface TestMapper {

    TestMapper MAPPER = Mappers.getMapper(TestMapper.class);

    TestEntity toEntity(Test dto);

    Test toDto(TestEntity entity);
}
