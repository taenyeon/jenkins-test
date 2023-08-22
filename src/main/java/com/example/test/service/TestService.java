package com.example.test.service;

import com.example.test.domain.dto.Test;
import com.example.test.domain.entity.TestEntity;
import com.example.test.domain.mapper.TestMapper;
import com.example.test.domain.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestMapper mapper = TestMapper.MAPPER;
    private final TestRepository testRepository;

    public int create(String val) {
        TestEntity entity = mapper.toEntity(new Test(val));
        TestEntity saved = testRepository.save(entity);
        return saved.getSeq();
    }

    public Test findById(int seq) {
        TestEntity testEntity = testRepository.findById(seq).orElse(null);
        return mapper.toDto(testEntity);
    }

    public List<Test> findAll() {
        List<TestEntity> entityList = testRepository.findAll();
        return entityList.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean deleteById(int seq) {
        try {
            testRepository.deleteById(seq);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
