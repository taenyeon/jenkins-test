package com.example.test.controller;

import com.example.test.domain.dto.Test;
import com.example.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("")
    public ResponseEntity<Integer> create(@RequestBody String val){
        int seq = testService.create(val);
        return ResponseEntity.ok(seq);
    }

    @GetMapping("{seq}")
    public ResponseEntity<Test> findById(@PathVariable String seq){
        Test test = testService.findById(Integer.parseInt(seq));
        return ResponseEntity.ok(test);
    }

    @GetMapping("")
    public ResponseEntity<List<Test>> findAll(){
        List<Test> testList = testService.findAll();
        return ResponseEntity.ok(testList);
    }

    @DeleteMapping("{seq}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String seq){
        boolean isDeleted = testService.deleteById(Integer.parseInt(seq));
        return ResponseEntity.ok(isDeleted);
    }


}
