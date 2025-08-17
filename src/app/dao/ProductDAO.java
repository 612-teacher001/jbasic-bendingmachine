package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.bean.Product;
import app.common.Display;

public class ProductDAO {
	
	/**
	 * クラス定数
	 */
	// データベース接続情報文字列定数群
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/jbasicdaodb";
	private static final String DB_USER = "student";
	private static final String DB_PASSWORD = "himitu";
	
	private Connection conn;
	
	/**
	 * コンストラクタ
	 */
	public ProductDAO() {
		this.getConnection();
	}

	/**
	 * データベース接続オブジェクトを取得する
	 */
	private void getConnection() {
		try {
			this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
	}

	/**
	 * データベース接続オブジェクトを解放する
	 */
	private void close() {
		try {
			if (this.conn != null) this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * すべての商品を取得する
	 * @return 商品リスト
	 */
	public List<Product> findAll() {
		
		// 1. 実行するSQLの設定
		String sql = "SELECT * FROM products ORDER BY id";
		try (// 2. SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(sql);
			 // 3. SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();
			) {
			// 4. 結果セットから商品リストへの詰替え
			List<Product> list = new ArrayList<Product>();
			while (rs.next()) {
				Product bean = new Product();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setPrice(rs.getInt("price"));
				bean.setQuantity(rs.getInt("quantity"));
				list.add(bean);
			}
			// 5. 商品リストの返却
			return list;
		} catch (SQLException e) {
			Display.showMessageln("レコード取得時にエラーが発生しました。");
			System.exit(0);
			return null;
		}
		
	}
	
	
	
}
