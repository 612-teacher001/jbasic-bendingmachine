package app.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import app.common.helper.StringLengthHelper;

class FixedLengthGeneratorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("FixedLengthGenerator#execute(String, int)メソッドのテストクラス")
	class ExecuteStringTest {
		@ParameterizedTest
		@MethodSource("dataProvider")
		@DisplayName("Test: 指定された文字数の文字列に変換できる")
		void test(StringLengthHelper target, String expected) {
			// execute
			String actual = FixedLengthGenerator.execute(target.getValue(), target.getLength());
			// verify
			assertEquals(expected, actual);
		}
		
		static Stream<Arguments> dataProvider() {
			// setup
			List<StringLengthHelper> target = new ArrayList<>();
			List<String> expected = new ArrayList<String>();
			
			// Test01【指定文字数以下の文字数の文字列リテラルの変換】文字列「abc」を10文字固定長文字列に変換すると「abc       」である
			target.add(new StringLengthHelper("abc", 10));
			expected.add("abc       ");
			// Test02【指定文字数と同じ文字数の文字列リテラルの変換】文字列「watermelon」を10文字固定長文字列に変換すると「watermelon」である
			target.add(new StringLengthHelper("watermelon", 10));
			expected.add("watermelon");
			// Test03【指定文字数より長い文字数の文字列リテラルの変換】文字列「watermelonman」を10文字固定長文字列に変換すると「watermelon」である
			target.add(new StringLengthHelper("watermelonman", 10));
			expected.add("watermelon");
			// Test04【nullの変換】nullを10文字固定長文字列に変換すると文字列「          」である
			target.add(new StringLengthHelper(null, 10));
			expected.add("          ");
			// Test05【空文字列の変換】空文字列「」を10文字固定長文字列に変換すると文字列「          」である
			target.add(new StringLengthHelper("", 10));
			expected.add("          ");
			
			// テストパラメータを返却
			int count = target.size();
			return Stream.of(
						  Arguments.of(target.get(0), expected.get(0))
						, Arguments.of(target.get(1), expected.get(1))
						, Arguments.of(target.get(2), expected.get(2))
						, Arguments.of(target.get(3), expected.get(3))
						, Arguments.of(target.get(4), expected.get(4))
						, Arguments.of(target.get(count - 1), expected.get(count -1))
					);
		}
	}
	
	

}
