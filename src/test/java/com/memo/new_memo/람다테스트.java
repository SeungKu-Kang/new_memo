package com.memo.new_memo;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class 람다테스트 {

	@Test
	void 람다테스트1() {
		// 람다, stream
		List<String> fruits = List.of("apple", "banana", "cherry");
		fruits
		.stream()
		.filter(element -> element.startsWith("b"))
		.forEach(element -> log.info("### {}" , element));
		
	}
	
	@Test
	void 람다테스트2() {
		List<String> fruits = List.of("apple", "banana", "cherry");
		
		fruits = fruits
		.stream()
		.map(element -> element.toUpperCase())
		.collect(Collectors.toList()); // stream to list
		
		log.info("$$$ {}" , fruits);
	}
}
