package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WaiFuCBSJEntity {
    private long _id;
    private int _renWuBH;
    private String _ch;
    private int _ceNeiXH;
    private String _cid;
    private String _st;
    private int _zhangWuNY;
    private int _chaoBiaoN;
    private int _chaoBiaoY;
    private int _chaoCi;
    private long _chaoBiaoRQ;
    private int _shangCiCM;
    private int _benCiCM;
    private int _chaoJianSL;
    private int _zhuangtaibm;
    private String _zhuangtaimc;
    private long _shangCiCBRQ;
    private int _shangCiZTBM;
    private String _shangCiZTMC;
    private int _shangCiCJSL;
    private int _shangCiZTLXS;
    private int _pingjunl1;
    private int _pingjunl2;
    private int _pingjunl3;
    private double _je;
    private String _zongbiaocid;
    private String _chaobiaoy;
    private int _iChaobiaobz;
    private int _jiubiaocm;
    private int _xinbiaodm;
    private long _HUANBIAORQ;
    private int _fangshibm;
    private int _lianggaoldyybm;
    private int _chaobiaoid;
    private int _zhuangtailxs;
    private int _shuibiaobl;
    private double _yongshuizkl;
    private double _paishuizkl;
    private int _tiaojiah;
    private String _jianhao;
    private long _paifasj;
    private int _lingyongslsm;
    private int _lianggaosl;
    private int _liangdisl;
    private String _x1;
    private String _y1;
    private String _x;
    private String _y;
    private String _sChaobiaobz;
    private int _ceneipx;
    private String _waiFuDengJR;
    private long _waiFuDengJRQ;
    private String _waiFuDengJYY;
    private String _waiFuYMC;
    private String _waiFuYBH;
    private long _waiFuQX;
    private int _waiFuZT;
    private int _waiFuCM;
    private String _sWaiFuCBZT;
    private int _iWaiFuCBZT;
    private int _waiFuCJSL;
    private String _waiFuBZ;
    private int _shangChuanBZ;

    public WaiFuCBSJEntity() {

    }

    public WaiFuCBSJEntity(long _id,
                           int _renWuBH,
                           String _ch,
                           int _ceNeiXH,
                           String _cid,
                           String _st,
                           int _zhangWuNY,
                           int _chaoBiaoN,
                           int _chaoBiaoY,
                           int _chaoCi,
                           long _chaoBiaoRQ,
                           int _shangCiCM,
                           int _benCiCM,
                           int _chaoJianSL,
                           int _zhuangtaibm,
                           String _zhuangtaimc,
                           long _shangCiCBRQ,
                           int _shangCiZTBM,
                           String _shangCiZTMC,
                           int _shangCiCJSL,
                           int _shangCiZTLXS,
                           int _pingjunl1,
                           int _pingjunl2,
                           int _pingjunl3,
                           double _je,
                           String _zongbiaocid,
                           String _chaobiaoy,
                           int _iChaobiaobz,
                           int _jiubiaocm,
                           int _xinbiaodm,
                           long _HUANBIAORQ,
                           int _fangshibm,
                           int _lianggaoldyybm,
                           int _chaobiaoid,
                           int _zhuangtailxs,
                           int _shuibiaobl,
                           double _yongshuizkl,
                           double _paishuizkl,
                           int _tiaojiah,
                           String _jianhao,
                           long _paifasj,
                           int _lingyongslsm,
                           int _lianggaosl,
                           int _liangdisl,
                           String _x1,
                           String _y1,
                           String _x,
                           String _y,
                           String _sChaobiaobz,
                           int _ceneipx,
                           String _waiFuDengJR,
                           long _waiFuDengJRQ,
                           String _waiFuDengJYY,
                           String _waiFuYMC,
                           String _waiFuYBH,
                           long _waiFuQX,
                           int _waiFuZT,
                           int _waiFuCM,
                           String _sWaiFuCBZT,
                           int _iWaiFuCBZT,
                           int _waiFuCJSL,
                           String _waiFuBZ,
                           int _shangChuanBZ) {
        this._id = _id;
        this._renWuBH = _renWuBH;
        this._ch = _ch;
        this._ceNeiXH = _ceNeiXH;
        this._cid = _cid;
        this._st = _st;
        this._zhangWuNY = _zhangWuNY;
        this._chaoBiaoN = _chaoBiaoN;
        this._chaoBiaoY = _chaoBiaoY;
        this._chaoCi = _chaoCi;
        this._chaoBiaoRQ = _chaoBiaoRQ;
        this._shangCiCM = _shangCiCM;
        this._benCiCM = _benCiCM;
        this._chaoJianSL = _chaoJianSL;
        this._zhuangtaibm = _zhuangtaibm;
        this._zhuangtaimc = _zhuangtaimc;
        this._shangCiCBRQ = _shangCiCBRQ;
        this._shangCiZTBM = _shangCiZTBM;
        this._shangCiZTMC = _shangCiZTMC;
        this._shangCiCJSL = _shangCiCJSL;
        this._shangCiZTLXS = _shangCiZTLXS;
        this._pingjunl1 = _pingjunl1;
        this._pingjunl2 = _pingjunl2;
        this._pingjunl3 = _pingjunl3;
        this._je = _je;
        this._zongbiaocid = _zongbiaocid;
        this._chaobiaoy = _chaobiaoy;
        this._iChaobiaobz = _iChaobiaobz;
        this._jiubiaocm = _jiubiaocm;
        this._xinbiaodm = _xinbiaodm;
        this._HUANBIAORQ = _HUANBIAORQ;
        this._fangshibm = _fangshibm;
        this._lianggaoldyybm = _lianggaoldyybm;
        this._chaobiaoid = _chaobiaoid;
        this._zhuangtailxs = _zhuangtailxs;
        this._shuibiaobl = _shuibiaobl;
        this._yongshuizkl = _yongshuizkl;
        this._paishuizkl = _paishuizkl;
        this._tiaojiah = _tiaojiah;
        this._jianhao = _jianhao;
        this._paifasj = _paifasj;
        this._lingyongslsm = _lingyongslsm;
        this._lianggaosl = _lianggaosl;
        this._liangdisl = _liangdisl;
        this._x1 = _x1;
        this._y1 = _y1;
        this._x = _x;
        this._y = _y;
        this._sChaobiaobz = _sChaobiaobz;
        this._ceneipx = _ceneipx;
        this._waiFuDengJR = _waiFuDengJR;
        this._waiFuDengJRQ = _waiFuDengJRQ;
        this._waiFuDengJYY = _waiFuDengJYY;
        this._waiFuYMC = _waiFuYMC;
        this._waiFuYBH = _waiFuYBH;
        this._waiFuQX = _waiFuQX;
        this._waiFuZT = _waiFuZT;
        this._waiFuCM = _waiFuCM;
        this._sWaiFuCBZT = _sWaiFuCBZT;
        this._iWaiFuCBZT = _iWaiFuCBZT;
        this._waiFuCJSL = _waiFuCJSL;
        this._waiFuBZ = _waiFuBZ;
        this._shangChuanBZ = _shangChuanBZ;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int get_renWuBH() {
        return _renWuBH;
    }

    public void set_renWuBH(int _renWuBH) {
        this._renWuBH = _renWuBH;
    }

    public String get_ch() {
        return _ch;
    }

    public void set_ch(String _ch) {
        this._ch = _ch;
    }

    public int get_ceNeiXH() {
        return _ceNeiXH;
    }

    public void set_ceNeiXH(int _ceNeiXH) {
        this._ceNeiXH = _ceNeiXH;
    }

    public String get_cid() {
        return _cid;
    }

    public void set_cid(String _cid) {
        this._cid = _cid;
    }

    public String get_st() {
        return _st;
    }

    public void set_st(String _st) {
        this._st = _st;
    }

    public int get_zhangWuNY() {
        return _zhangWuNY;
    }

    public void set_zhangWuNY(int _zhangWuNY) {
        this._zhangWuNY = _zhangWuNY;
    }

    public int get_chaoBiaoN() {
        return _chaoBiaoN;
    }

    public void set_chaoBiaoN(int _chaoBiaoN) {
        this._chaoBiaoN = _chaoBiaoN;
    }

    public int get_chaoBiaoY() {
        return _chaoBiaoY;
    }

    public void set_chaoBiaoY(int _chaoBiaoY) {
        this._chaoBiaoY = _chaoBiaoY;
    }

    public int get_chaoCi() {
        return _chaoCi;
    }

    public void set_chaoCi(int _chaoCi) {
        this._chaoCi = _chaoCi;
    }

    public long get_chaoBiaoRQ() {
        return _chaoBiaoRQ;
    }

    public void set_chaoBiaoRQ(long _chaoBiaoRQ) {
        this._chaoBiaoRQ = _chaoBiaoRQ;
    }

    public int get_shangCiCM() {
        return _shangCiCM;
    }

    public void set_shangCiCM(int _shangCiCM) {
        this._shangCiCM = _shangCiCM;
    }

    public int get_benCiCM() {
        return _benCiCM;
    }

    public void set_benCiCM(int _benCiCM) {
        this._benCiCM = _benCiCM;
    }

    public int get_chaoJianSL() {
        return _chaoJianSL;
    }

    public void set_chaoJianSL(int _chaoJianSL) {
        this._chaoJianSL = _chaoJianSL;
    }

    public int get_zhuangtaibm() {
        return _zhuangtaibm;
    }

    public void set_zhuangtaibm(int _zhuangtaibm) {
        this._zhuangtaibm = _zhuangtaibm;
    }

    public String get_zhuangtaimc() {
        return _zhuangtaimc;
    }

    public void set_zhuangtaimc(String _zhuangtaimc) {
        this._zhuangtaimc = _zhuangtaimc;
    }

    public long get_shangCiCBRQ() {
        return _shangCiCBRQ;
    }

    public void set_shangCiCBRQ(long _shangCiCBRQ) {
        this._shangCiCBRQ = _shangCiCBRQ;
    }

    public int get_shangCiZTBM() {
        return _shangCiZTBM;
    }

    public void set_shangCiZTBM(int _shangCiZTBM) {
        this._shangCiZTBM = _shangCiZTBM;
    }

    public String get_shangCiZTMC() {
        return _shangCiZTMC;
    }

    public void set_shangCiZTMC(String _shangCiZTMC) {
        this._shangCiZTMC = _shangCiZTMC;
    }

    public int get_shangCiCJSL() {
        return _shangCiCJSL;
    }

    public void set_shangCiCJSL(int _shangCiCJSL) {
        this._shangCiCJSL = _shangCiCJSL;
    }

    public int get_shangCiZTLXS() {
        return _shangCiZTLXS;
    }

    public void set_shangCiZTLXS(int _shangCiZTLXS) {
        this._shangCiZTLXS = _shangCiZTLXS;
    }

    public int get_pingjunl1() {
        return _pingjunl1;
    }

    public void set_pingjunl1(int _pingjunl1) {
        this._pingjunl1 = _pingjunl1;
    }

    public int get_pingjunl2() {
        return _pingjunl2;
    }

    public void set_pingjunl2(int _pingjunl2) {
        this._pingjunl2 = _pingjunl2;
    }

    public int get_pingjunl3() {
        return _pingjunl3;
    }

    public void set_pingjunl3(int _pingjunl3) {
        this._pingjunl3 = _pingjunl3;
    }

    public double get_je() {
        return _je;
    }

    public void set_je(double _je) {
        this._je = _je;
    }

    public String get_zongbiaocid() {
        return _zongbiaocid;
    }

    public void set_zongbiaocid(String _zongbiaocid) {
        this._zongbiaocid = _zongbiaocid;
    }

    public String get_chaobiaoy() {
        return _chaobiaoy;
    }

    public void set_chaobiaoy(String _chaobiaoy) {
        this._chaobiaoy = _chaobiaoy;
    }

    public int get_iChaobiaobz() {
        return _iChaobiaobz;
    }

    public void set_iChaobiaobz(int _iChaobiaobz) {
        this._iChaobiaobz = _iChaobiaobz;
    }

    public int get_jiubiaocm() {
        return _jiubiaocm;
    }

    public void set_jiubiaocm(int _jiubiaocm) {
        this._jiubiaocm = _jiubiaocm;
    }

    public int get_xinbiaodm() {
        return _xinbiaodm;
    }

    public void set_xinbiaodm(int _xinbiaodm) {
        this._xinbiaodm = _xinbiaodm;
    }

    public long get_HUANBIAORQ() {
        return _HUANBIAORQ;
    }

    public void set_HUANBIAORQ(long _HUANBIAORQ) {
        this._HUANBIAORQ = _HUANBIAORQ;
    }

    public int get_fangshibm() {
        return _fangshibm;
    }

    public void set_fangshibm(int _fangshibm) {
        this._fangshibm = _fangshibm;
    }

    public int get_lianggaoldyybm() {
        return _lianggaoldyybm;
    }

    public void set_lianggaoldyybm(int _lianggaoldyybm) {
        this._lianggaoldyybm = _lianggaoldyybm;
    }

    public int get_chaobiaoid() {
        return _chaobiaoid;
    }

    public void set_chaobiaoid(int _chaobiaoid) {
        this._chaobiaoid = _chaobiaoid;
    }

    public int get_zhuangtailxs() {
        return _zhuangtailxs;
    }

    public void set_zhuangtailxs(int _zhuangtailxs) {
        this._zhuangtailxs = _zhuangtailxs;
    }

    public int get_shuibiaobl() {
        return _shuibiaobl;
    }

    public void set_shuibiaobl(int _shuibiaobl) {
        this._shuibiaobl = _shuibiaobl;
    }

    public double get_yongshuizkl() {
        return _yongshuizkl;
    }

    public void set_yongshuizkl(double _yongshuizkl) {
        this._yongshuizkl = _yongshuizkl;
    }

    public double get_paishuizkl() {
        return _paishuizkl;
    }

    public void set_paishuizkl(double _paishuizkl) {
        this._paishuizkl = _paishuizkl;
    }

    public int get_tiaojiah() {
        return _tiaojiah;
    }

    public void set_tiaojiah(int _tiaojiah) {
        this._tiaojiah = _tiaojiah;
    }

    public String get_jianhao() {
        return _jianhao;
    }

    public void set_jianhao(String _jianhao) {
        this._jianhao = _jianhao;
    }

    public long get_paifasj() {
        return _paifasj;
    }

    public void set_paifasj(long _paifasj) {
        this._paifasj = _paifasj;
    }

    public int get_lingyongslsm() {
        return _lingyongslsm;
    }

    public void set_lingyongslsm(int _lingyongslsm) {
        this._lingyongslsm = _lingyongslsm;
    }

    public int get_lianggaosl() {
        return _lianggaosl;
    }

    public void set_lianggaosl(int _lianggaosl) {
        this._lianggaosl = _lianggaosl;
    }

    public int get_liangdisl() {
        return _liangdisl;
    }

    public void set_liangdisl(int _liangdisl) {
        this._liangdisl = _liangdisl;
    }

    public String get_x1() {
        return _x1;
    }

    public void set_x1(String _x1) {
        this._x1 = _x1;
    }

    public String get_y1() {
        return _y1;
    }

    public void set_y1(String _y1) {
        this._y1 = _y1;
    }

    public String get_x() {
        return _x;
    }

    public void set_x(String _x) {
        this._x = _x;
    }

    public String get_y() {
        return _y;
    }

    public void set_y(String _y) {
        this._y = _y;
    }

    public String get_sChaobiaobz() {
        return _sChaobiaobz;
    }

    public void set_sChaobiaobz(String _sChaobiaobz) {
        this._sChaobiaobz = _sChaobiaobz;
    }

    public int get_ceneipx() {
        return _ceneipx;
    }

    public void set_ceneipx(int _ceneipx) {
        this._ceneipx = _ceneipx;
    }

    public String get_waiFuDengJR() {
        return _waiFuDengJR;
    }

    public void set_waiFuDengJR(String _waiFuDengJR) {
        this._waiFuDengJR = _waiFuDengJR;
    }

    public long get_waiFuDengJRQ() {
        return _waiFuDengJRQ;
    }

    public void set_waiFuDengJRQ(long _waiFuDengJRQ) {
        this._waiFuDengJRQ = _waiFuDengJRQ;
    }

    public String get_waiFuDengJYY() {
        return _waiFuDengJYY;
    }

    public void set_waiFuDengJYY(String _waiFuDengJYY) {
        this._waiFuDengJYY = _waiFuDengJYY;
    }

    public String get_waiFuYMC() {
        return _waiFuYMC;
    }

    public void set_waiFuYMC(String _waiFuYMC) {
        this._waiFuYMC = _waiFuYMC;
    }

    public String get_waiFuYBH() {
        return _waiFuYBH;
    }

    public void set_waiFuYBH(String _waiFuYBH) {
        this._waiFuYBH = _waiFuYBH;
    }

    public long get_waiFuQX() {
        return _waiFuQX;
    }

    public void set_waiFuQX(long _waiFuQX) {
        this._waiFuQX = _waiFuQX;
    }

    public int get_waiFuZT() {
        return _waiFuZT;
    }

    public void set_waiFuZT(int _waiFuZT) {
        this._waiFuZT = _waiFuZT;
    }

    public int get_waiFuCM() {
        return _waiFuCM;
    }

    public void set_waiFuCM(int _waiFuCM) {
        this._waiFuCM = _waiFuCM;
    }

    public String get_sWaiFuCBZT() {
        return _sWaiFuCBZT;
    }

    public void set_sWaiFuCBZT(String _sWaiFuCBZT) {
        this._sWaiFuCBZT = _sWaiFuCBZT;
    }

    public int get_iWaiFuCBZT() {
        return _iWaiFuCBZT;
    }

    public void set_iWaiFuCBZT(int _iWaiFuCBZT) {
        this._iWaiFuCBZT = _iWaiFuCBZT;
    }

    public int get_waiFuCJSL() {
        return _waiFuCJSL;
    }

    public void set_waiFuCJSL(int _waiFuCJSL) {
        this._waiFuCJSL = _waiFuCJSL;
    }

    public String get_waiFuBZ() {
        return _waiFuBZ;
    }

    public void set_waiFuBZ(String _waiFuBZ) {
        this._waiFuBZ = _waiFuBZ;
    }

    public int get_shangChuanBZ() {
        return _shangChuanBZ;
    }

    public void set_shangChuanBZ(int _shangChuanBZ) {
        this._shangChuanBZ = _shangChuanBZ;
    }

    public static WaiFuCBSJEntity fromJSON(JSONObject object) {
        WaiFuCBSJEntity waiFuCBSJEntity = new WaiFuCBSJEntity();

        waiFuCBSJEntity.set_id(object.optInt("iD"));
        waiFuCBSJEntity.set_renWuBH(object.optInt("i_RenWuBH"));
        waiFuCBSJEntity.set_ch(object.optString("s_CH"));
        waiFuCBSJEntity.set_ceNeiXH(object.optInt("i_CeNeiXH"));
        waiFuCBSJEntity.set_cid(object.optString("s_CID"));
        waiFuCBSJEntity.set_st(object.optString("s_ST"));
        waiFuCBSJEntity.set_zhangWuNY(object.optInt("i_ZhangWuNY"));
        waiFuCBSJEntity.set_chaoBiaoN(object.optInt("i_ChaoBiaoN"));
        waiFuCBSJEntity.set_chaoBiaoY(object.optInt("i_ChaoBiaoY"));
        waiFuCBSJEntity.set_chaoCi(object.optInt("i_ChaoCi"));
        waiFuCBSJEntity.set_chaoBiaoRQ(object.optLong("d_ChaoBiaoRQ"));
        waiFuCBSJEntity.set_shangCiCM(object.optInt("i_ShangCiCM"));
        waiFuCBSJEntity.set_benCiCM(object.optInt("i_BenCiCM"));
        waiFuCBSJEntity.set_chaoJianSL(object.optInt("i_ChaoJianSL"));
        waiFuCBSJEntity.set_zhuangtaibm(object.optInt("i_ZHUANGTAIBM"));
        waiFuCBSJEntity.set_zhuangtaimc(object.optString("s_ZHUANGTAIMC"));
        waiFuCBSJEntity.set_shangCiCBRQ(object.optLong("d_ShangCiCBRQ"));
        waiFuCBSJEntity.set_shangCiZTBM(object.optInt("i_ShangCiZTBM"));
        waiFuCBSJEntity.set_shangCiZTMC(object.optString("s_ShangCiZTMC"));
        waiFuCBSJEntity.set_shangCiCJSL(object.optInt("i_ShangCiCJSL"));
        waiFuCBSJEntity.set_shangCiZTLXS(object.optInt("i_ShangCiZTLXS"));
        waiFuCBSJEntity.set_pingjunl1(object.optInt("i_PINGJUNL1"));
        waiFuCBSJEntity.set_pingjunl2(object.optInt("i_PINGJUNL2"));
        waiFuCBSJEntity.set_pingjunl3(object.optInt("i_PINGJUNL3"));
        waiFuCBSJEntity.set_je(object.optDouble("n_JE"));
        waiFuCBSJEntity.set_zongbiaocid(object.optString("s_ZONGBIAOCID"));
        waiFuCBSJEntity.set_chaobiaoy(object.optString("s_CHAOBIAOY"));
        waiFuCBSJEntity.set_iChaobiaobz(object.optInt("i_CHAOBIAOBZ"));
        waiFuCBSJEntity.set_jiubiaocm(object.optInt("i_JIUBIAOCM"));
        waiFuCBSJEntity.set_xinbiaodm(object.optInt("i_XINBIAODM"));
        waiFuCBSJEntity.set_HUANBIAORQ(object.optLong("d_HUANBIAORQ"));
        waiFuCBSJEntity.set_fangshibm(object.optInt("i_FANGSHIBM"));
        waiFuCBSJEntity.set_lianggaoldyybm(object.optInt("i_LIANGGAOLDYYBM"));
        waiFuCBSJEntity.set_chaobiaoid(object.optInt("i_CHAOBIAOID"));
        waiFuCBSJEntity.set_zhuangtailxs(object.optInt("i_ZHUANGTAILXS"));
        waiFuCBSJEntity.set_shuibiaobl(object.optInt("i_SHUIBIAOBL"));
        waiFuCBSJEntity.set_yongshuizkl(object.optDouble("n_YONGSHUIZKL"));
        waiFuCBSJEntity.set_paishuizkl(object.optDouble("n_PAISHUIZKL"));
        waiFuCBSJEntity.set_tiaojiah(object.optInt("i_TIAOJIAH"));
        waiFuCBSJEntity.set_jianhao(object.optString("s_JIANHAO"));
        waiFuCBSJEntity.set_paifasj(object.optLong("d_PAIFASJ"));
        waiFuCBSJEntity.set_lingyongslsm(object.optInt("i_LINGYONGSLSM"));
        waiFuCBSJEntity.set_lianggaosl(object.optInt("i_LIANGGAOSL"));
        waiFuCBSJEntity.set_liangdisl(object.optInt("i_LIANGDISL"));
        waiFuCBSJEntity.set_x1(object.optString("s_X1"));
        waiFuCBSJEntity.set_y1(object.optString("s_Y1"));
        waiFuCBSJEntity.set_x(object.optString("s_X"));
        waiFuCBSJEntity.set_y(object.optString("s_Y"));
        waiFuCBSJEntity.set_sChaobiaobz(object.optString("s_CHAOBIAOBZ"));
        waiFuCBSJEntity.set_ceneipx(object.optInt("i_CeNeiPX"));
        waiFuCBSJEntity.set_waiFuDengJR(object.optString("s_WaiFuDengJR"));
        waiFuCBSJEntity.set_waiFuDengJRQ(object.optLong("d_WaiFuDengJRQ"));
        waiFuCBSJEntity.set_waiFuDengJYY(object.optString("s_WaiFuDengJYY"));
        waiFuCBSJEntity.set_waiFuYMC(object.optString("s_WaiFuYMC"));
        waiFuCBSJEntity.set_waiFuYBH(object.optString("s_WaiFuYBH"));
        waiFuCBSJEntity.set_waiFuQX(object.optLong("d_WaiFuQX"));
        waiFuCBSJEntity.set_waiFuZT(object.optInt("i_WaiFuZT"));
        waiFuCBSJEntity.set_waiFuCM(object.optInt("i_WaiFuCM"));
        waiFuCBSJEntity.set_sWaiFuCBZT(object.optString("s_WaiFuCBZT"));
        waiFuCBSJEntity.set_iWaiFuCBZT(object.optInt("i_WaiFuCBZT"));
        waiFuCBSJEntity.set_waiFuCJSL(object.optInt("i_WaiFuCJSL"));
        waiFuCBSJEntity.set_waiFuBZ(object.optString("s_WaiFuBZ"));
        waiFuCBSJEntity.set_shangChuanBZ(object.optInt("i_ShangChuanBZ"));

        return waiFuCBSJEntity;
    }

    public static List<WaiFuCBSJEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<WaiFuCBSJEntity> list = new ArrayList<WaiFuCBSJEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            WaiFuCBSJEntity waiFuCBSJEntity = WaiFuCBSJEntity.fromJSON(object);
            list.add(waiFuCBSJEntity);
        }
        return list;
    }

    public JSONObject toJSON(WaiFuCBSJEntity waiFuCBSJEntity) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("ID", waiFuCBSJEntity.get_id());
        object.put("ID", waiFuCBSJEntity.get_id());
        object.put("I_RenWuBH", waiFuCBSJEntity.get_renWuBH());
        object.put("S_CH", waiFuCBSJEntity.get_ch());
        object.put("I_CeNeiXH", waiFuCBSJEntity.get_ceNeiXH());
        object.put("S_CID", waiFuCBSJEntity.get_cid());
        object.put("S_ST", waiFuCBSJEntity.get_st());
        object.put("I_ZhangWuNY", waiFuCBSJEntity.get_zhangWuNY());
        object.put("I_ChaoBiaoN", waiFuCBSJEntity.get_chaoBiaoN());
        object.put("I_ChaoBiaoY", waiFuCBSJEntity.get_chaoBiaoY());
        object.put("I_ChaoCi", waiFuCBSJEntity.get_chaoCi());
        object.put("D_ChaoBiaoRQ", waiFuCBSJEntity.get_chaoBiaoRQ());
        object.put("I_ShangCiCM", waiFuCBSJEntity.get_shangCiCM());
        object.put("I_BenCiCM", waiFuCBSJEntity.get_benCiCM());
        object.put("I_ChaoJianSL", waiFuCBSJEntity.get_chaoJianSL());
        object.put("I_ZHUANGTAIBM", waiFuCBSJEntity.get_zhuangtaibm());
        object.put("S_ZHUANGTAIMC", waiFuCBSJEntity.get_zhuangtaimc());
        object.put("D_ShangCiCBRQ", waiFuCBSJEntity.get_shangCiCBRQ());
        object.put("I_ShangCiZTBM", waiFuCBSJEntity.get_shangCiZTBM());
        object.put("S_ShangCiZTMC", waiFuCBSJEntity.get_shangCiZTMC());
        object.put("I_ShangCiCJSL", waiFuCBSJEntity.get_shangCiCJSL());
        object.put("I_ShangCiZTLXS", waiFuCBSJEntity.get_shangCiZTLXS());
        object.put("I_PINGJUNL1", waiFuCBSJEntity.get_pingjunl1());
        object.put("I_PINGJUNL2", waiFuCBSJEntity.get_pingjunl2());
        object.put("I_PINGJUNL3", waiFuCBSJEntity.get_pingjunl3());
        object.put("N_JE", waiFuCBSJEntity.get_je());
        object.put("S_ZONGBIAOCID", waiFuCBSJEntity.get_zongbiaocid());
        object.put("S_CHAOBIAOY", waiFuCBSJEntity.get_chaobiaoy());
        object.put("I_CHAOBIAOBZ", waiFuCBSJEntity.get_iChaobiaobz());
        object.put("I_JIUBIAOCM", waiFuCBSJEntity.get_jiubiaocm());
        object.put("I_XINBIAODM", waiFuCBSJEntity.get_xinbiaodm());
        object.put("D_HUANBIAORQ", waiFuCBSJEntity.get_HUANBIAORQ());
        object.put("I_FANGSHIBM", waiFuCBSJEntity.get_fangshibm());
        object.put("I_LIANGGAOLDYYBM", waiFuCBSJEntity.get_lianggaoldyybm());
        object.put("I_CHAOBIAOID", waiFuCBSJEntity.get_chaobiaoid());
        object.put("I_ZHUANGTAILXS", waiFuCBSJEntity.get_zhuangtailxs());
        object.put("I_SHUIBIAOBL", waiFuCBSJEntity.get_shuibiaobl());
        object.put("N_YONGSHUIZKL", waiFuCBSJEntity.get_yongshuizkl());
        object.put("N_PAISHUIZKL", waiFuCBSJEntity.get_paishuizkl());
        object.put("I_TIAOJIAH", waiFuCBSJEntity.get_tiaojiah());
        object.put("S_JIANHAO", waiFuCBSJEntity.get_jianhao());
        object.put("D_PAIFASJ", waiFuCBSJEntity.get_paifasj());
        object.put("I_LINGYONGSLSM", waiFuCBSJEntity.get_lingyongslsm());
        object.put("I_LIANGGAOSL", waiFuCBSJEntity.get_lianggaosl());
        object.put("I_LIANGDISL", waiFuCBSJEntity.get_liangdisl());
        object.put("S_X1", waiFuCBSJEntity.get_x1());
        object.put("S_Y1", waiFuCBSJEntity.get_y1());
        object.put("S_X", waiFuCBSJEntity.get_x());
        object.put("S_Y", waiFuCBSJEntity.get_y());
        object.put("S_CHAOBIAOBZ", waiFuCBSJEntity.get_sChaobiaobz());
        object.put("I_CeNeiPX", waiFuCBSJEntity.get_ceneipx());
        object.put("S_WaiFuDengJR", waiFuCBSJEntity.get_waiFuDengJR());
        object.put("D_WaiFuDengJRQ", waiFuCBSJEntity.get_waiFuDengJRQ());
        object.put("S_WaiFuDengJYY", waiFuCBSJEntity.get_waiFuDengJYY());
        object.put("S_WaiFuYMC", waiFuCBSJEntity.get_waiFuYMC());
        object.put("S_WaiFuYBH", waiFuCBSJEntity.get_waiFuYBH());
        object.put("D_WaiFuQX", waiFuCBSJEntity.get_waiFuQX());
        object.put("I_WaiFuZT", waiFuCBSJEntity.get_waiFuZT());
        object.put("I_WaiFuCM", waiFuCBSJEntity.get_waiFuCM());
        object.put("S_WaiFuCBZT", waiFuCBSJEntity.get_sWaiFuCBZT());
        object.put("I_WaiFuCBZT", waiFuCBSJEntity.get_iWaiFuCBZT());
        object.put("I_WaiFuCJSL", waiFuCBSJEntity.get_waiFuCJSL());
        object.put("S_WaiFuBZ", waiFuCBSJEntity.get_waiFuBZ());
        object.put("I_ShangChuanBZ", waiFuCBSJEntity.get_shangChuanBZ());

        return object;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();

        object.put("ID", get_id());
        object.put("ID", get_id());
        object.put("I_RenWuBH", get_renWuBH());
        object.put("S_CH", get_ch());
        object.put("I_CeNeiXH", get_ceNeiXH());
        object.put("S_CID", get_cid());
        object.put("S_ST", get_st());
        object.put("I_ZhangWuNY", get_zhangWuNY());
        object.put("I_ChaoBiaoN", get_chaoBiaoN());
        object.put("I_ChaoBiaoY", get_chaoBiaoY());
        object.put("I_ChaoCi", get_chaoCi());
        object.put("D_ChaoBiaoRQ", get_chaoBiaoRQ());
        object.put("I_ShangCiCM", get_shangCiCM());
        object.put("I_BenCiCM", get_benCiCM());
        object.put("I_ChaoJianSL", get_chaoJianSL());
        object.put("I_ZHUANGTAIBM", get_zhuangtaibm());
        object.put("S_ZHUANGTAIMC", get_zhuangtaimc());
        object.put("D_ShangCiCBRQ", get_shangCiCBRQ());
        object.put("I_ShangCiZTBM", get_shangCiZTBM());
        object.put("S_ShangCiZTMC", get_shangCiZTMC());
        object.put("I_ShangCiCJSL", get_shangCiCJSL());
        object.put("I_ShangCiZTLXS", get_shangCiZTLXS());
        object.put("I_PINGJUNL1", get_pingjunl1());
        object.put("I_PINGJUNL2", get_pingjunl2());
        object.put("I_PINGJUNL3", get_pingjunl3());
        object.put("N_JE", get_je());
        object.put("S_ZONGBIAOCID", get_zongbiaocid());
        object.put("S_CHAOBIAOY", get_chaobiaoy());
        object.put("I_CHAOBIAOBZ", get_iChaobiaobz());
        object.put("I_JIUBIAOCM", get_jiubiaocm());
        object.put("I_XINBIAODM", get_xinbiaodm());
        object.put("D_HUANBIAORQ", get_HUANBIAORQ());
        object.put("I_FANGSHIBM", get_fangshibm());
        object.put("I_LIANGGAOLDYYBM", get_lianggaoldyybm());
        object.put("I_CHAOBIAOID", get_chaobiaoid());
        object.put("I_ZHUANGTAILXS", get_zhuangtailxs());
        object.put("I_SHUIBIAOBL", get_shuibiaobl());
        object.put("N_YONGSHUIZKL", get_yongshuizkl());
        object.put("N_PAISHUIZKL", get_paishuizkl());
        object.put("I_TIAOJIAH", get_tiaojiah());
        object.put("S_JIANHAO", get_jianhao());
        object.put("D_PAIFASJ", get_paifasj());
        object.put("I_LINGYONGSLSM", get_lingyongslsm());
        object.put("I_LIANGGAOSL", get_lianggaosl());
        object.put("I_LIANGDISL", get_liangdisl());
        object.put("S_X1", get_x1());
        object.put("S_Y1", get_y1());
        object.put("S_X", get_x());
        object.put("S_Y", get_y());
        object.put("S_CHAOBIAOBZ", get_sChaobiaobz());
        object.put("I_CeNeiPX", get_ceneipx());
        object.put("S_WaiFuDengJR", get_waiFuDengJR());
        object.put("D_WaiFuDengJRQ", get_waiFuDengJRQ());
        object.put("S_WaiFuDengJYY", get_waiFuDengJYY());
        object.put("S_WaiFuYMC", get_waiFuYMC());
        object.put("S_WaiFuYBH", get_waiFuYBH());
        object.put("D_WaiFuQX", get_waiFuQX());
        object.put("I_WaiFuZT", get_waiFuZT());
        object.put("I_WaiFuCM", get_waiFuCM());
        object.put("S_WaiFuCBZT", get_sWaiFuCBZT());
        object.put("I_WaiFuCBZT", get_iWaiFuCBZT());
        object.put("I_WaiFuCJSL", get_waiFuCJSL());
        object.put("S_WaiFuBZ", get_waiFuBZ());
        object.put("I_ShangChuanBZ", get_shangChuanBZ());

        return object;
    }

    public static JSONArray toJSONArray(List<WaiFuCBSJEntity> waiFuCBSJEntityList)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (WaiFuCBSJEntity waiFuCBSJEntity : waiFuCBSJEntityList) {
            JSONObject object = waiFuCBSJEntity.toJSON(waiFuCBSJEntity);
            array.put(object);
        }

        return array;
    }
}
