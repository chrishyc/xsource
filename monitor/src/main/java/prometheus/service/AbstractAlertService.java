package prometheus.service;

import com.xiaomi.mifi.admin.model.common.Response;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.FeiShuNoticeParam;

/**
 * @author zht
 * @date 2020-7-14 11:02:33
 */
public interface AbstractAlertService {

  /**
   * 发送飞书通知
   *
   * @param param 飞书所需参数
   * @return
   */
  Response sendNotice(FeiShuNoticeParam param);

  Response sendNotice(String json);

  /**
   * 通过飞书群名称获取飞书群ID
   *
   * @param groupName
   * @return
   */
  Response getGroupId(String groupName);

}
