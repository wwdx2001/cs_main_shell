/**
 *
 */
package com.sh3h.serverprovider.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author liukaiyu
 *
 */
public class ChaoBiaoRWEntity {
	/**
	 * 抄表任务id，自增长ID
	 */
	private int _id;

	/**
	 * 任务编号
	 */
	private int _renWuBH;
	/**
	 * 抄表员编号
	 */
	private String _chaoBiaoYBH;
	/**
	 * 派发时间
	 */
	private long _paiFaSJ;

	/**
	 * 抄表员姓名
	 */
	private String _chaoBiaoYXM;

	public String getchaoBiaoYXM() {
		return _chaoBiaoYXM;
	}

	public void setchaoBiaoYXM(String _chaoBiaoYXM) {
		this._chaoBiaoYXM = _chaoBiaoYXM;
	}

	public int getRenWuBH() {
		return _renWuBH;
	}

	public void setRenWuBH(int _renWuBH) {
		this._renWuBH = _renWuBH;
	}

	public String getChaoBiaoYBH() {
		return _chaoBiaoYBH;
	}

	public void setChaoBiaoYBH(String _chaoBiaoYBH) {
		this._chaoBiaoYBH = _chaoBiaoYBH;
	}

	public long getPaiFaSJ() {
		return _paiFaSJ;
	}

	public void setPaiFaSJ(long _paiFaSJ) {
		this._paiFaSJ = _paiFaSJ;
	}

	/**
	 * 账务年月 ：201308
	 */
	private int _zhangWuNY;

	/**
	 * 册本号，册本编号，允许出现字母
	 */
	private String _cH;

	/**
	 * 册本名称
	 */
	private String _ceBenMC;

	/**
	 * 工次
	 */
	private int _gongCi;

	/**
	 * 站点
	 */
	private String _sT;

	/**
	 * 总数
	 */
	private int _zongShu;

	/**
	 * 已抄数量
	 */
	private int _yiChaoShu;

	/**
	 * 抄表周期
	 */
	private String _chaoBiaoZQ;

	/**
	 * @return the _id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void setId(int _id) {
		this._id = _id;
	}

	/**
	 * @return the _zhangWuNY
	 */
	public int getZhangWuNY() {
		return _zhangWuNY;
	}

	/**
	 * @param _zhangWuNY
	 *            the _zhangWuNY to set
	 */
	public void setZhangWuNY(int _zhangWuNY) {
		this._zhangWuNY = _zhangWuNY;
	}

	/**
	 * @return the _cH
	 */
	public String getCH() {
		return _cH;
	}

	/**
	 * @param _cH
	 *            the _cH to set
	 */
	public void setCH(String _cH) {
		this._cH = _cH;
	}

	/**
	 * @return the _ceBenMC
	 */
	public String getCeBenMC() {
		return _ceBenMC;
	}

	/**
	 * @param _ceBenMC
	 *            the _ceBenMC to set
	 */
	public void setCeBenMC(String _ceBenMC) {
		this._ceBenMC = _ceBenMC;
	}

	/**
	 * @return the _gongCi
	 */
	public int getGongCi() {
		return _gongCi;
	}

	/**
	 * @param _gongCi
	 *            the _gongCi to set
	 */
	public void setGongCi(int _gongCi) {
		this._gongCi = _gongCi;
	}

	/**
	 * @return the _sT
	 */
	public String getST() {
		return _sT;
	}

	/**
	 * @param _sT
	 *            the _sT to set
	 */
	public void setST(String _sT) {
		this._sT = _sT;
	}

	/**
	 * @return the _zongShu
	 */
	public int getZongShu() {
		return _zongShu;
	}

	/**
	 * @param _zongShu
	 *            the _zongShu to set
	 */
	public void setZongShu(int _zongShu) {
		this._zongShu = _zongShu;
	}

	/**
	 * @return the _yiChaoShu
	 */
	public int getYiChaoShu() {
		return _yiChaoShu;
	}

	/**
	 * @param _yiChaoShu
	 *            the _yiChaoShu to set
	 */
	public void setYiChaoShu(int _yiChaoShu) {
		this._yiChaoShu = _yiChaoShu;
	}

	/**
	 * @return the _chaoBiaoZQ
	 */
	public String getChaoBiaoZQ() {
		return _chaoBiaoZQ;
	}

	/**
	 * @param _chaoBiaoZQ
	 *            the _chaoBiaoZQ to set
	 */
	public void setChaoBiaoZQ(String _chaoBiaoZQ) {
		this._chaoBiaoZQ = _chaoBiaoZQ;
	}

	/**
	 * 转换JSONObject对象为ChaoBiaoRWEntity
	 *
	 * @param object
	 * @return
	 */
	public static ChaoBiaoRWEntity fromJSON(JSONObject object)
			throws JSONException {
		ChaoBiaoRWEntity cbrw = new ChaoBiaoRWEntity();

		cbrw.setCeBenMC(object.optString("s_CeBenMC"));
		cbrw.setCH(object.optString("s_CH"));
		cbrw.setChaoBiaoYBH(object.optString("s_ChaoBiaoYBH"));
		cbrw.setchaoBiaoYXM(object.optString("s_ChaoBiaoYXM"));
		cbrw.setChaoBiaoZQ(object.optString("s_CHAOBIAOZQ"));
		cbrw.setGongCi(object.optInt("i_GongCi"));
		cbrw.setId(object.optInt("iD"));
		cbrw.setPaiFaSJ(object.optLong("d_PaiFaSJ"));
		cbrw.setRenWuBH(object.optInt("i_RenWuBH"));
		cbrw.setST(object.optString("s_ST"));
		cbrw.setYiChaoShu(object.optInt("i_YiChaoShu"));
		cbrw.setZhangWuNY(object.optInt("i_ZhangWuNY"));
		cbrw.setZongShu(object.optInt("i_ZongShu"));

		return cbrw;
	}

	/**
	 * 转换JSONArray对象为List<ChaoBiaoRWEntity>
	 *
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	public static List<ChaoBiaoRWEntity> fromJSONArray(JSONArray array)
			throws JSONException {
		List<ChaoBiaoRWEntity> list = new ArrayList<ChaoBiaoRWEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			ChaoBiaoRWEntity cbsj = ChaoBiaoRWEntity.fromJSON(object);
			list.add(cbsj);
		}
		return list;
	}

}
