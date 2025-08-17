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
	@DisplayName("productDAO#finndById(int)メソッドのテストクラス")
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
