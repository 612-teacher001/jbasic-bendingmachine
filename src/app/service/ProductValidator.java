package app.service;

import app.bean.Product;

public class ProductValidator {
	
	/**
	 * フィールド
	 */
	private Product product; // 検査対象商品インスタンス
	private int charge;      // 残額

	/**
	 * コンストラクタ
	 * @param product 検査大砲商品インスタンス
	 * @param charge  残額
	 */
	public ProductValidator(Product product, int charge) {
		this.product = product;
		this.charge = charge;
	}

	/**
	 * 商品インスタンスフィールドがnullであるかどうかを判定する
	 * @return 商品インスタンスがnullである場合はtrue、それ以外はfalse
	 */
	public boolean isNotFound() {
		return (this.product == null);
	}

	/**
	 * 商品インスタンスフィールドが売り切れであるかどうかを判定する
	 * @return 商品の数量が0の場合はtrue、それ以外はfalse
	 */
	public boolean isSoldout() {
		return (this.product.getQuantity() <= 0);
	}

	/**
	 * 商品を購入できるかどうかを判定する
	 * @param charge 残高
	 * @param price 商品の価格
	 * @return 残高が商品の価格以上である場合はtrue、それ以外はfalse
	 */
	public boolean isInsufficientCharge() {
		return (this.charge < this.product.getPrice());
	}

	/**
	 * 商品の妥当性を検証する
	 * @return エラーメッセージ
	 */
	public String check() {
		
		String errorMessage;
		
		if (this.isNotFound()) {
			// 1. 商品が存在しない場合 → 誤ったIDを入力したケース。これ以上進められないので終了
			errorMessage = "該当する商品がありません。";
		} else if (this.isSoldout()) {
			// 2. 在庫切れの場合 → 数量が0なので購入不可能
			errorMessage = "売り切れです。購入できません。";
		} else if (this.isInsufficientCharge()) {
			// 3. 残金不足の場合 → 購入金額より少ないので処理を進められない
			errorMessage = "お金が足りません。購入できません。";
		} else {
			errorMessage = "";
		}
		
		return errorMessage;

	}

}
