package app.dao.helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 単体テストのためのproductsテーブルの準備を担当するHelperクラス
 */
public class ProductsTableHelper {

	private static final Charset CHARSET_MS932 = Charset.forName("MS932");
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/jbasicdaodb";
	private static final String DB_USER = "student";
	private static final String DB_PASSWORD = "himitu";
	private static final String DB_SCRIPT_PATH = "dev-memo/db/insert_recs.sql";
	private static final String SQL_SPLITTER = ";";
	
	/**
	 * 単体テストの準備
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void setUpTable() throws IOException, SQLException {
		initTable();
	}
	
	/**
	 * 単体テストの後始末
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void tearDownTable() throws IOException, SQLException {
		initTable();
	}
	
	/**
	 * productsテーブルの初期化を行う
	 * @throws IOException
	 * @throws SQLException
	 */
	private static void initTable() throws IOException, SQLException {
		String script = Files.readString(Path.of(DB_SCRIPT_PATH), CHARSET_MS932);
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 Statement stmt = conn.createStatement();) {
			for (String sql : script.split(SQL_SPLITTER)) {
				sql = sql.trim();
				if (!sql.isEmpty()) {
					stmt.executeUpdate(sql);
				}
			}
		}
	}

}
