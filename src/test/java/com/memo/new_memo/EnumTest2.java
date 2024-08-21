package com.memo.new_memo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class EnumTest2 {

	public enum CalcType {
		// 열거형 정의
		CALC_A(value -> value), // ramda식이 적용이 안되는 낮은 버전의 자바인 경우 ()대신 {} 안에 함수를 만들어야 한다 
		CALC_B(value -> value * 10),
		CALC_C(value -> value * 3),
		CALC_ETC(value -> 0);
		
		// 필드 정의 => enum에 정의된 function
		private Function<Integer, Integer> expression;
		
		// 생성자
		CalcType(Function<Integer, Integer> expression) {
			this.expression = expression;
		}
		
		// 계산 적용 메소드
		public int calculate(int value) {
			return expression.apply(value);
		}
	}
	
	@Test
	void 계산테스트() {
		// given 
		CalcType ctype = CalcType.CALC_A;
		
		// when
		int result = ctype.calculate(500);
		
		// then
		assertEquals(1500, result);
		
	}
}
