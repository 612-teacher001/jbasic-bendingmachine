package app.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
