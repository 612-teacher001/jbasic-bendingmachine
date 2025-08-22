package app.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import app.bean.Product;
import app.dao.helper.ProductsTableHelper;

class ProductDaoTest {

	/** テスト対象クラス：system under test */
	private ProductDAO sut;
	
	@BeforeEach
	void setUp() throws Exception {
		// テスト対象テーブルの初期化
		ProductsTableHelper.setUpTable();
		// テスト対象クラスのインスタンス化
		sut = new ProductDAO();
	}

	@AfterEach
	void tearDown() throws Exception {
		// テスト対象テーブルの原状復帰
		ProductsTableHelper.tearDownTable();
		// テスト対象クラスの解放
		sut.close();
	}
	
	@Nested
	@DisplayName("ProductDAO#deleteByid(int)メソッドのテストクラス")
	class DleteByIdTest {
		@Test
		@DisplayName("Test53: 登録されていない商品IDを指定すると削除されない")
		void test53() {
			// setup
			int targetId = -1;
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("こーいお茶", 120, 9));
			expectedList.add(new Product("ボズ", 120, 0));
			
			// execute
			sut.deleteById(targetId);
			List<Product> actualList = sut.findAll();
			
			// verify
			for (int i = 0; i < actualList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
			
		}
		@Test
		@DisplayName("Test52: 商品「オロナミンZ」と商品「こってり煮込んだお味噌汁」を追加したあとで商品「オロナミンZ」を削除できる")
		void test52() {
			// setup
			Product[] targets = {new Product("オロナミンZ", 230, 40), new Product("こってり煮込んだお味噌汁", 270, 36)};
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("こーいお茶", 120, 9));
			expectedList.add(new Product("ボズ", 120, 0));
			expectedList.add(targets[1]);
			
			// execute
			for (Product target : targets) {
				sut.save(target);
			}
			List<Product> target = sut.findByName("オロナミン");
			sut.deleteById(target.get(0).getId());
			List<Product> actualList = sut.findAll();
			
			// verify
			for (int i = 0; i < actualList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
			
		}
		
		@Test
		@DisplayName("Test51: 商品ID「3」の商品を削除できる")
		void test51() {
			// setup
			int targetId = 3;
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("ボズ", 120, 0));
			// execute
			sut.deleteById(targetId);
			List<Product> actualList = sut.findAll();
			// verify
			for (int i = 0; i < actualList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
			
		}
	}
	
	@Nested
	@DisplayName("ProductDAO#save(Product)メソッドのテストクラス")
	class SaveTest {
		/** テスト補助変数 */
		List<Product> expectedList = new ArrayList<Product>();
		
		@ParameterizedTest
		@MethodSource("dataProvider42")
		@DisplayName("Test42:【レコード更新のテスト】")
		void test42(Product target, List<Product> expectedList) {
			// execute
			sut.save(target);
			List<Product> actualList = sut.findAll();
			// verify
			for (int i = 0; i < actualList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
		}
		
		static Stream<Arguments> dataProvider42() {
			// setup
			List<Product> target = new ArrayList<Product>();
			List<Product> expectedList = new ArrayList<Product>();
			List<List<Product>> expected = new ArrayList<List<Product>>();
			
			// Test42.1: 商品ID「1」の商品名を「ペプシコ」に更新できる
			expectedList = new ArrayList<Product>();
			target.add(new Product(1, "ペプシコ", 160, 6));
			expectedList.add(new Product("ペプシコ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("こーいお茶", 120, 9));
			expectedList.add(new Product("ボズ", 120, 0));
			expected.add(expectedList);
			
			// Test42.2: 商品ID「2」の価格を「170」に更新できる
			expectedList = new ArrayList<Product>();
			target.add(new Product(2, "アグエリアス", 170, 10));
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 170, 10));
			expectedList.add(new Product("こーいお茶", 120, 9));
			expectedList.add(new Product("ボズ", 120, 0));
			expected.add(expectedList);
			
			// Test42.3: 商品ID「3」の価格を「170」数量を「12」に更新できる
			expectedList = new ArrayList<Product>();
			target.add(new Product(3, "こーいお茶", 170, 12));
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("こーいお茶", 170, 12));
			expectedList.add(new Product("ボズ", 120, 0));
			expected.add(expectedList);
			
			// テストパラメータの返却
			int count = target.size();
			return Stream.of(
					  Arguments.of(target.get(0), expected.get(0))
					, Arguments.of(target.get(1), expected.get(1))
					, Arguments.of(target.get(count - 1), expected.get(count - 1))
				);
		}
		
		@Test
		@DisplayName("Test41:【レコード新規登録のテスト】")
		void test41() {
			// setup
			Product target = new Product("こってり煮込んだお味噌汁", 140, 3);
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("こーいお茶", 120, 9));
			expectedList.add(new Product("ボズ", 120, 0));
			expectedList.add(target);
			
			// execute
			sut.save(target);
			List<Product> actualList = sut.findAll();
			
			// verify
			for (int i = 0; i < actualList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
			
		}
	}

	@Nested
	@DisplayName("ProductDAO#findByName(String)メソッドのテストクラス")
	class FindByNameTest {
		@ParameterizedTest
		@MethodSource("dataProvider")
		@DisplayName("Test31: 商品名に指定されたキーワードが含まれている商品を取得できる")
		void test31(String targetWord, List<Product> expectedList) {
			// execute
			List<Product> actualList = sut.findByName(targetWord);
			// verify
			for (int i = 0; i < actualList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
		}
		
		static Stream<Arguments> dataProvider() {
			// setup
			List<String> target = new ArrayList<String>();
			List<Product> products = null;
			List<List<Product>> expected = new ArrayList<List<Product>>();
			
			// Test31: キーワード「コーラ」で「ゴカコーラ」を取得できる
			target.add("コーラ");
			products = new ArrayList<Product>();
			products.add(new Product("ゴカコーラ", 160, 6));
			expected.add(products);
			// Test32: キーワード「エリア」で「アグエリアス」を取得できる
			target.add("エリア");
			products = new ArrayList<Product>();
			products.add(new Product("アグエリアス", 150, 10));
			expected.add(products);
			
			// テストパラメータの返却
			int count = target.size();
			return Stream.of(
						  Arguments.of(target.get(0), expected.get(0))
						, Arguments.of(target.get(count - 1), expected.get(count - 1))
					);
		}
	}
	
	@Nested
	@DisplayName("ProductDAO#findById(int)メソッドのテストクラス")
	class FindByIdTest {
		@Test
		@DisplayName("Test22: 登録されていない商品IDを指定するとnullが返る")
		void test22() {
			// setup
			int targetId = -1;
			// execute
			Product actual = sut.findById(targetId);
			// verify
			assertNull(actual);
		}
		@ParameterizedTest
		@MethodSource("dataProvider")
		@DisplayName("Test21: 登録されている商品を取得できる")
		void test21(int targetId, Product expected) {
			// execute
			Product actual = sut.findById(targetId);
			// verify
			assertEquals(expected.toStringCompare(), actual.toStringCompare());
		}
		
		static Stream<Arguments> dataProvider() {
			// setup
			List<Integer> target = new ArrayList<Integer>();
			List<Product> expected = new ArrayList<Product>();
			
			// Test21: 商品ID「2」の商品は「アグエリアス」である
			target.add(2);
			expected.add(new Product("アグエリアス", 150, 10));
			// Test22: 商品ID「4」の商品は「ボズ」である
			target.add(4);
			expected.add(new Product("ボズ", 120, 0));
			
			// テストパラメータの返却
			int count = target.size();
			return Stream.of(
						  Arguments.of(target.get(0), expected.get(0))
						, Arguments.of(target.get(count - 1), expected.get(count - 1))
					);
		}
		
	}
	
	@Nested
	@DisplayName("ProductDAO#findAll()メソッドのテストクラス")
	class FindAllTest {
		@Test
		@DisplayName("Test11: すべての商品を取得できる")
		void test01() {
			// setup
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(new Product("ゴカコーラ", 160, 6));
			expectedList.add(new Product("アグエリアス", 150, 10));
			expectedList.add(new Product("こーいお茶", 120, 9));
			expectedList.add(new Product("ボズ", 120, 0));
			// execute
			List<Product> actualList = sut.findAll();
			
			// verify
			for (int i = 0; i < expectedList.size(); i++) {
				Product actual = actualList.get(i);
				Product expected = expectedList.get(i);
				assertEquals(expected.toStringCompare(), actual.toStringCompare());
			}
			
		}
		
	}

}
