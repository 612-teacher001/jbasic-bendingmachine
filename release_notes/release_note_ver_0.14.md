# リリースノート

## Ver.0.14（MLS14：cannondale）

### リリース日：2028.08.18 Mon.

### コミットID：
  - [13b9f09](https://github.com/612-teacher001/jbasic-bendingmachine/commit/13b9f09)：商品全件検索メソッドの実装（身につけてほしいコーディングスキルの目安）
  - [8953bfa](https://github.com/612-teacher001/jbasic-bendingmachine/commit/8953bfa)：商品全件検索メソッドの実装
  - [947029d](https://github.com/612-teacher001/jbasic-bendingmachine/commit/947029d)：商品の主キー検索メソッドの実装
  - [9be87aa](https://github.com/612-teacher001/jbasic-bendingmachine/commit/9be87aa)：商品名のキーワード検索メソッドの実装
  - [9181c12](https://github.com/612-teacher001/jbasic-bendingmachine/commit/9181c12)：コメントを追加した実装の最終形態


### 概要

課題1-4の実施版です。

### リリース内容

  - `app.dao.ProductDAO` クラス：productsテーブルにアクセスしてレコードを操作を実行するDAO
  - `app.dao.ProductDaoTest` クラス：ProductDAOの単体テストクラ
  - `app.dao.helper.ProductsTableHelper` クラス：productsテーブルにサンプルレコードを登録するHelperクラス



### 関連スキル項目

  - try-with-resourcesの使い方
  - JDBCを利用したデータベースアクセス
  - プレースホルダ付きSQLとSQL実行オブジェクト

---

[変更履歴に戻る](../change.log.md)