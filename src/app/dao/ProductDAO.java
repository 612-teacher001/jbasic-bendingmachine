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

/**
 * 商品データベースアクセス用 DAO クラス
 */
public class ProductDAO {

    // -----------------------------
    // データベース接続情報
    // -----------------------------
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/jbasicdaodb";
    private static final String DB_USER = "student";
    private static final String DB_PASSWORD = "himitu";

    // -----------------------------
    // SQL 定数
    // -----------------------------
    private static final String SQL_FIND_ALL = "SELECT id, name, price, quantity FROM products ORDER BY id";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, price, quantity FROM products WHERE id = ?";
    private static final String SQL_FIND_BY_NAME = "SELECT id, name, price, quantity FROM products WHERE name LIKE ?";
    private static final String SQL_UPDATE = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
    private static final String SQL_INSERT = "INSERt INTO products (name, price, quantity) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM products WHERE id = ?";

    // -----------------------------
    // フィールド
    // -----------------------------
    private Connection conn;

    // -----------------------------
    // コンストラクタ
    // -----------------------------
    public ProductDAO() {
        this.getConnection();
    }

    /**
     * データベース接続を取得する
     */
    private void getConnection() {
        try {
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            Display.showMessageln("データベース接続に失敗しました。");
        }
    }

    /**
     * データベース接続を解放する
     */
    public void close() {
        try {
            if (this.conn != null) this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // -----------------------------
    // 商品削除メソッド
    // -----------------------------
    
    /**
     * 指定された商品IDを指定するとの商品を削除する
     * @param id 削除対象商品の商品ID
     */
	public void deleteById(int id) {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_DELETE_BY_ID);) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Display.showMessageln("レコード削除時にエラーが発生しました。");
		}
	}
    
    // -----------------------------
    // 商品追加/商品更新メソッド
    // -----------------------------
    
    /**
     * 商品の追加または更新を行う
     * @param product 操作対象商品インスタンス
     */
	public void save(Product product) {
		boolean updated = isUpdated(product);
		// 三項演算子については、多用すると可読性が落ちるがこの程度なら許容できる（catchブロック内の三項演算子も同様）
		String sql = updated ? SQL_UPDATE : SQL_INSERT;
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getQuantity());
			if (updated) {
				// 更新の場合はWHERE句のプレースホルダを置換する必要がある
				pstmt.setInt(4, product.getId());
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			String errorMessage = updated
				? "レコード更新時にエラーが発生しました。"  // UPDATE文の場合
				: "レコード挿入時にエラーが発生しました。"; // INSERT文の場合
            Display.showMessageln(errorMessage);
		}
	}
    
    // -----------------------------
    // 商品取得メソッド
    // -----------------------------
    
    /**
     * すべての商品を取得する
     * @return 商品リスト
     */
    public List<Product> findAll() {
        // try-with-resources を使う理由:
        // PreparedStatement や ResultSet は使用後に必ず close() が必要。
        // try-with-resources により、自動でリソースを解放し、リソースリークを防ぐ。
        try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            // convertToList / convertRecordToBean に切り出す理由:
            // 1. ResultSet から Product を生成する処理を共通化できる
            // 2. findAll / findById / findByName などで再利用できる
            // 3. 将来フィールドが増えても、このメソッドだけ修正すれば済む
            return convertToList(rs);

        } catch (SQLException e) {
            Display.showMessageln("レコード取得時にエラーが発生しました。");
            return null;
        }
    }

    /**
     * 指定された商品IDの商品を取得する
     * @param id 取得する商品の商品ID
     * @return 商品インスタンス、存在しなければ null
     */
    public Product findById(int id) {
        try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                // 単件取得の場合は convertToBean を使用
                return convertToBean(rs);
            }

        } catch (SQLException e) {
            Display.showMessageln("レコード取得時にエラーが発生しました。");
            return null;
        }
    }

    /**
     * 商品名にキーワードが含まれる商品を取得する
     * @param keyword キーワード
     * @return 該当商品リスト
     */
    public List<Product> findByName(String keyword) {
        try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_NAME)) {

            // ワイルドカードはここで付与する
            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                return convertToList(rs);
            }

        } catch (SQLException e) {
            Display.showMessageln("レコード取得時にエラーが発生しました: " + e.getMessage());
            return null;
        }
    }
    
    // -----------------------------
    // ResultSet 変換メソッド
    // -----------------------------

    /**
     * ResultSet から単一 Product を生成する
     * @param rs ResultSet
     * @return Product、存在しなければ null
     * @throws SQLException
     */
    private Product convertToBean(ResultSet rs) throws SQLException {
        Product bean = null;
        if (rs.next()) {
            bean = new Product();
            convertRecordToBean(rs, bean);
        }
        return bean;
    }

    /**
     * ResultSet から複数 Product を生成する
     * @param rs ResultSet
     * @return Product リスト
     * @throws SQLException
     */
    private List<Product> convertToList(ResultSet rs) throws SQLException {
        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            Product bean = new Product();
            convertRecordToBean(rs, bean);
            list.add(bean);
        }
        return list;
    }

    /**
     * ResultSet の1レコードを Product にマッピングする
     * convertToBean / convertToList から呼ばれる
     * @param rs ResultSet
     * @param bean Product
     * @throws SQLException
     */
    private void convertRecordToBean(ResultSet rs, Product bean) throws SQLException {
        // なぜ切り出すか：
        // 1. ResultSet から Product フィールドに値をセットする処理を共通化
        // 2. 単件・複数件取得で共通コードを使い回せる
        // 3. 将来フィールド追加時はこのメソッドのみ修正すれば良い
        bean.setId(rs.getInt("id"));
        bean.setName(rs.getString("name"));
        bean.setPrice(rs.getInt("price"));
        bean.setQuantity(rs.getInt("quantity"));
    }

    /**
     * 指定された商品インスタンスが更新用であるかどうかを判定する
     * @param product 判定対象商品インスタンス
     * @return 商品インスタンスのidフィールドが1以上の整数である場合はtrue、それ以外の場合はfalse
     */
    private boolean isUpdated(Product product) {
    	if (product.getId() > 0) {
    		return true;
    	} else {
    		return false;
    	}
    }

}
