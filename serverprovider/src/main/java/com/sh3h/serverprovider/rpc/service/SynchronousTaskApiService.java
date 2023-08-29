/**
 *
 */
package com.sh3h.serverprovider.rpc.service;

import android.util.Base64;

import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.ChaoBiaoRWEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoSJEntity;
import com.sh3h.serverprovider.entity.GongGaoXXEntity;
import com.sh3h.serverprovider.entity.RenWuXXEntity;
import com.sh3h.serverprovider.entity.WaiFuCBSJEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liukaiyu
 */
public class SynchronousTaskApiService extends BaseApiService {

    private static final String URL = "SynchronousTask.ashx";

    private static final String METHOD_GETRENWUBHBYCHAOBIAOY = "getRenWuBHByChaoBiaoY";
    private static final String METHOD_GETCURRENTRWBYCHAOBIAOY = "getCurrentRWByChaoBiaoY";

    private static final String METHOD_UPDATECHAOBIAOSJTOSERVER = "updateChaoBiaoSJToServer";

    private static final String METHOD_DOWNLOADCHAOBIAORW = "downLoadChaoBiaoRW";

    private static final String METHOD_DOWNLOADCHAOBIAOSJ = "downLoadChaoBiaoSJ";

    private static final String METHOD_UPLOADSINGLECHAOBIAOSJ = "upLoadSingleChaoBiaoSJ";

    // 获取公告信息
    private static final String METHOD_TONGBUGONGGAOXX = "getGongGaoXX";
    private static final String METHOD_GETGONGGAOBYBH = "getGongGaoByGongGaoBH";

    // 上传图片
    private static final String METHOD_UPLOADFILETOSERVER = "upLoadFileToServer";

    // 下载临时册本
    private static final String METHOD_DOWNLOADTEMPORARYRECORDS = "downloadTemporaryRecords";

    // upload the file
    private static final String METHOD_UPLOADFILE = "upLoadFile";

    private static final String METHOD_SYNCWAIFUCBSJLIST = "syncWaiFuCBSJList";

    @Override
    public String getHandlerURL() {
        return URL;
    }

    /**
     * 查询抄表员当前任务编号方法 返回字符串为多个任务编号拼接，已逗号分隔，例如：1,3,6
     *
     * @param Account
     * @return
     * @throws ApiException
     */
    public RenWuXXEntity getRenWuBHByChaoBiaoY(String Account) throws JSONException, ApiException {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETRENWUBHBYCHAOBIAOY,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前任务编号方法调用失败", e);
            throw e;
        }

        RenWuXXEntity renWuXXEntity = null;
        if (jsonObject != null) {
            renWuXXEntity = RenWuXXEntity.fromJSON(jsonObject);
        }

        return renWuXXEntity;
    }

    /**
     * 查询抄表员当前任务列表方法
     *
     * @param Account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoRWEntity> getCurrentRWByChaoBiaoY(String Account)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_GETCURRENTRWBYCHAOBIAOY,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前任务列表方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoRWEntity> list = new ArrayList<ChaoBiaoRWEntity>();
        if (array != null) {
            list = ChaoBiaoRWEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 同步上传更新抄表数据
     *
     * @param list
     * @param deviceID
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> updateChaoBiaoSJToServer(
            List<ChaoBiaoSJEntity> list, String deviceID, String s_chaobiaoybh,
            int renwubh) throws JSONException, ApiException {
        JSONArray array;
        ChaoBiaoSJEntity cbsjentity = new ChaoBiaoSJEntity();
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_UPDATECHAOBIAOSJTOSERVER,
                    cbsjentity.toJSONArray(list), deviceID, s_chaobiaoybh,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传更新抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> returnlist = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            returnlist = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return returnlist;
    }

    /**
     * 下载新任务
     *
     * @param renwubh
     * @param deviceid
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoRWEntity> downLoadChaoBiaoRW(int renwubh,
                                                     String deviceid, String account) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADCHAOBIAORW,
                    renwubh, deviceid, account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新任务方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoRWEntity> list = new ArrayList<ChaoBiaoRWEntity>();
        if (array != null) {
            list = ChaoBiaoRWEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载新抄表数据
     *
     * @param renwubh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> downLoadChaoBiaoSJ(int renwubh)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADCHAOBIAOSJ,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> list = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            list = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 单条抄表数据上传
     *
     * @param cbsjentity
     * @param deviceID
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public int upLoadSingleChaoBiaoSJ(ChaoBiaoSJEntity cbsjentity,
                                      String deviceID) throws JSONException, ApiException {
        // 返回码信息如下：
        // 1 上传成功，需要回写本地上传标志
        // 0 上传失败，该抄表数据已审核，需要回写本地上传和审核标志
        // -1 上传失败，该抄表数据所在的任务已完成，需要清理本地该任务编号下的数据
        // -2 上传失败，该抄表数据所在的任务已撤销，需要清理本地该任务编号下的数据
        try {
            return this.callInt(
                    SynchronousTaskApiService.METHOD_UPLOADSINGLECHAOBIAOSJ,
                    cbsjentity.toJSON(), deviceID);
        } catch (ApiException e) {
            LogUtil.e("API", "单条抄表数据上传方法调用失败", e);
            throw e;
        }

    }

    /**
     * 同步公告接口
     *
     * @param shebeibh
     * @param chaobiaoybh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<GongGaoXXEntity> tongBuGongGaoXX(String shebeibh,
                                                 String chaobiaoybh) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_TONGBUGONGGAOXX, shebeibh,
                    chaobiaoybh);
        } catch (ApiException e) {
            LogUtil.e("API", "同步公告方法调用失败", e);
            throw e;
        }

        List<GongGaoXXEntity> list = new ArrayList<GongGaoXXEntity>();
        if (array != null) {
            list = GongGaoXXEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 根据公告编号获取公告信息
     *
     * @param gonggaobh
     * @param chaobiaoybh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public GongGaoXXEntity getGongGaoXXByBH(int gonggaobh, String chaobiaoybh)
            throws JSONException, ApiException {
        JSONObject object;
        try {
            object = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETGONGGAOBYBH, gonggaobh,
                    chaobiaoybh);
        } catch (ApiException e) {
            LogUtil.e("API", "根据公告编号获取公告信息方法调用失败", e);
            throw e;
        }
        GongGaoXXEntity ggxx = new GongGaoXXEntity();
        if (object != null)
            ggxx = GongGaoXXEntity.fromJSON(object);

        return ggxx;
    }

    /**
     * 上传图片信息
     *
     * @param filename
     * @param s_ch
     * @param chaobiaoid
     * @param s_cid
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public boolean upLoadFileToServer(String filename,
                                      String s_ch,
                                      int chaobiaoid,
                                      String s_cid,
                                      byte[] buffer)
            throws JSONException, IOException {
        try {
            String array = Base64.encodeToString(buffer, Base64.DEFAULT);
            return this.callBoolean(
                    SynchronousTaskApiService.METHOD_UPLOADFILETOSERVER,
                    filename, s_ch, chaobiaoid, s_cid, array);
        } catch (ApiException e) {
            LogUtil.e("API", "上传图片信息方法调用失败", e);
            return false;
        }
    }

    /**
     * 下载临时册本抄表数据
     *
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> downTemporaryLoadChaoBiaoSJ(String account)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADTEMPORARYRECORDS,
                    account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载临时册本抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> list = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            list = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * upload the file
     * @param account
     * @param filename
     * @param buffer
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public boolean uploadFile(String account,
                              String filename,
                              byte[] buffer)
            throws JSONException, IOException {
        try {
            String array = Base64.encodeToString(buffer, Base64.DEFAULT);
            return this.callBoolean(
                    SynchronousTaskApiService.METHOD_UPLOADFILE,
                    account, filename, array);
        } catch (ApiException e) {
            LogUtil.e("API", "上传文件方法调用失败", e);
            return false;
        }
    }

    /**
     * 同步外复抄表数据
     * @param waiFuCBSJList
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<WaiFuCBSJEntity> syncWaiFuCBSJList(List<WaiFuCBSJEntity> waiFuCBSJList, String account)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_SYNCWAIFUCBSJLIST,
                    WaiFuCBSJEntity.toJSONArray(waiFuCBSJList), account);
        } catch (ApiException e) {
            LogUtil.e("API", "同步外复抄表数据方法调用失败", e);
            throw e;
        }

        List<WaiFuCBSJEntity> waiFuCBSJEntityList = new ArrayList<WaiFuCBSJEntity>();
        if (array != null) {
            waiFuCBSJEntityList = WaiFuCBSJEntity.fromJSONArray(array);
        }

        return waiFuCBSJEntityList;
    }
}
