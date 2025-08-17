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
	
	// SQL文字列定数群
	private static final String SQL_FIND_ALL = "SELECT * FROM products ORDER BY id";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM products WHERE id = ?";
	private static final String SQL_FIND_BY_NAME = "SELECT * FROM products WHERE name LIKE ?";
	
	/**
	 * フィールド：データベース接続オブジェクト
	 */
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
	public void close() {
		try {
			if (this.conn != null) this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> findByName(String keyword) {
		
		try (// 1. SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_NAME);) {
			// 2. プレースホルダをパラメータで置換
			pstmt.setString(1, "%" + keyword + "%"); // ワイルドカードはこのタイミングで追加
			try (// 3. SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 4. 結果セットから商品リストへの詰替え
				List<Product> list = this.convertToList(rs);
				// 5. 商品リストを返却
				return list;
			}
		} catch (SQLException e) {
			Display.showMessageln("レコード取得時にエラーが発生しました。");
			System.exit(0);
			return null;
		}
	}

	/**
	 * 指定された商品IDの商品を取得する
	 * @param id 取得する商品の商品ID
	 * @return 商品IDに合致する商品がある場合は商品インスタンス、それ以外はnull
	 */
	public Product findById(int id) {
		
		try (// 1. SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_ID);) {
			// 2. プレースホルダをパラメータで置換
			pstmt.setInt(1, id);
			try (// 3. SQLの実行と結果セットの取得
				 ResultSet rs = pstmt.executeQuery();) {
				// 4. 結果セットから商品インスタンスに変換
				List<Product> list = this.convertToList(rs);
				Product bean = null;
				if (list.size() > 0) {
					bean = list.get(0);
				}
				// 5. 商品インスタンスを返却
				return bean;
			}
		} catch (SQLException e) {
			Display.showMessageln("レコード取得時にエラーが発生しました。");
			System.exit(0);
			return null;
		}
	}
	
	/**
	 * すべての商品を取得する
	 * @return 商品リスト
	 */
	public List<Product> findAll() {
		
		try (// 1. SQL実行オブジェクトを取得
			 PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_ALL);
			 // 2. SQLの実行と結果セットの取得
			 ResultSet rs = pstmt.executeQuery();
			) {
			// 3. 結果セットから商品リストへの詰替え
			List<Product> list = this.convertToList(rs);
			// 4. 商品リストの返却
			return list;
		} catch (SQLException e) {
			Display.showMessageln("レコード取得時にエラーが発生しました。");
			System.exit(0);
			return null;
		}
		
	}

	/**
	 * 結果セットから商品インスタンスのリストにマッピングする
	 * （結果セットから商品インスタンスのリストに変換する）
	 * @param rs 結果セット
	 * @return 商品リスト
	 * @throws SQLException
	 */
	private List<Product> convertToList(ResultSet rs) throws SQLException {
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			Product bean = new Product();
			bean.setId(rs.getInt("id"));
			bean.setName(rs.getString("name"));
			bean.setPrice(rs.getInt("price"));
			bean.setQuantity(rs.getInt("quantity"));
			list.add(bean);
		}
		return list;
	}

}
