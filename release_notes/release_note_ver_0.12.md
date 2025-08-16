# リリースノート

## Ver.0.12（MLS12：giant）

### リリース日：2028.08.17 Sun.

### コミットID：
  - [25ebffa](https://github.com/612-teacher001/jbasic-bendingmachine/commit/25ebffa)：	本コミットは、この段階で身につけてほしいコーディングスキルの目安です。
  - [9679bec](https://github.com/612-teacher001/jbasic-bendingmachine/commit/9679bec)：本コミットは、コミットID [25ebffa](https://github.com/612-teacher001/jbasic-bendingmachine/commit/25ebffa) のソースコードについて、メソッドをに切り出し値を定数化することでコードの整理と保守性向上を図ってリファクタリングした例になっています。  以降のバージョンでは、このリファクタリング版をもとに管理していきます。

### 概要

課題1-2の実施版です。

メッセージを表示するDisplayクラスを追加しました。

### リリース内容

  - `app.client.StartUp` クラス：クライアント用プログラムを実行するクラス
  - `app.client.Menu` クラス：クライアント用メニューを表示するクラス
  - `app.common.Display` クラス：メッセージをディスプレイに表示するクラス
  - `app.service.PurchaseService` クラス：商品購入サービスを実行するクラス

### 関連スキル項目

  - 定数化
  - メソッド切り分け
  - switch文とdo-while文の利用
  - リファクタリング


---

[変更履歴に戻る](../change.log.md)