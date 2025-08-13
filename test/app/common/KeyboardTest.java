package app.common;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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

import app.common.helper.PromptResponse;

class KeyboardTest {

	/** テスト補助変数 */
	private InputStream originalIn;           // 標準入力の退避用
	private PrintStream originalOut;          // 標準出力の退避用
	private ByteArrayOutputStream outContent; // 標準出力捕捉用
	
	/**
	 * テスト準備処理：デフォルトの標準入出力の一時退避と標準出力捕捉用変数の初期化
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		// 標準湧出力の一時退避
		originalIn = System.in;
		originalOut = System.out;
		// 標準出力捕捉用変数の初期化
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * テスト事後処理：標準入出力の復元
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		// 標準入出力の復元
		System.setIn(originalIn);
		System.setOut(originalOut);
	}
	
	@Nested
	@DisplayName("Keyboard#getInputNumber()メソッドのテストクラス")
	class GetInputNumberTest {
		@ParameterizedTest
		@MethodSource("dataProvider")
		@DisplayName("表示メッセージと入力値が正しい")
		void test21(PromptResponse expected) throws Exception {
			// setup
			System.setIn(new ByteArrayInputStream((expected.getResponseln().getBytes())));
			int expectedInput = Integer.parseInt(expected.getResponse());
			// execute：疑似入力
			int actualInput = Keyboard.getInputNumber(expected.getPrompt());
			String actualMessage = outContent.toString();
			// verify
			assertThat("出力メッセージが正しいこと", actualMessage, is(expected.getPrompt()));
			assertThat("入力値が正しいこと", actualInput, is(expectedInput));
		}
		
		static Stream<Arguments> dataProvider() {
			// setup
			List<PromptResponse> expected = new ArrayList<PromptResponse>();
			// Test21: 「価格：」に対して「160」を入力できる
			expected.add(new PromptResponse("メニューを選択してください。-> ", "1"));
			// Test22: 「商品を入力してください。-> 」に対して「002」を入力できる
			expected.add(new PromptResponse("商品を購入するための金額を入力してください。-> ", "1000"));
			// Test23: 「商品を購入するための金額を入力してください。-> 」に対して「1000」を入力できる
			expected.add(new PromptResponse("商品を購入するための金額を入力してください。-> ", "1000"));
			// テストパラメータの返却
			int count = expected.size();
			return Stream.of(
					  Arguments.of(expected.get(0))
					, Arguments.of(expected.get(count - 1))
				   );
		}
	}

	@Nested
	@DisplayName("Keyboard#getInputString()メソッドのテストクラス")
	class GetInputStringTest {
		@ParameterizedTest
		@MethodSource("dataProvider")
		@DisplayName("表示メッセージと入力値が正しい")
		void test21(PromptResponse expected) throws Exception {
			// setup
			System.setIn(new ByteArrayInputStream((expected.getResponseln().getBytes())));
			String expectedInput = expected.getResponse();
			// execute：疑似入力
			String actualInput = Keyboard.getInputString(expected.getPrompt());
			String actualMessage = outContent.toString();
			// verify
			assertThat("出力メッセージが正しいこと", actualMessage, is(expected.getPrompt()));
			assertThat("入力値が正しいこと", actualInput, is(expectedInput));
		}
		
		static Stream<Arguments> dataProvider() {
			// setup
			List<PromptResponse> expected = new ArrayList<PromptResponse>();
			// Test11: 「商品：」に対して「ゴカコーラ」を入力できる
			expected.add(new PromptResponse("商品：", "ゴカコーラ"));
			// Test12: 「商品購入手続きを行いますか？」に対して「y」を入力できる
			expected.add(new PromptResponse("商品購入手続きを行いますか？", "y"));
			// テストパラメータの返却
			int count = expected.size();
			return Stream.of(
					  Arguments.of(expected.get(0))
					, Arguments.of(expected.get(count - 1))
					);
		}
	}
	
}
