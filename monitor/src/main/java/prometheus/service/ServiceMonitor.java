package prometheus.service;

import lombok.extern.slf4j.Slf4j;
import com.xiaomi.mifi.admin.model.common.Response;
import com.xiaomi.mifi.common.utils.http.MifiHttpClient;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.CardAction;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.CardConfig;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.CardHeader;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.CardText;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.FeiShuGroup;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.FeiShuGroupResponse;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.FeiShuNoticeParam;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.FeiShuTokenParam;
import com.xiaomi.mifi.thirdparty.proxy.model.feishu.FeishuCard;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ServiceMonitor implements AbstractAlertService {

  private static final String TOKEN_URL = "https://open.f.mioffice.cn/open-apis/auth/v3/tenant_access_token/internal/";
  private static final String SEND_URL = "https://open.f.mioffice.cn/open-apis/message/v4/send/";
  private static final String GROUP_URL = "https://open.f.mioffice.cn/open-apis/chat/v4/list";
  private static final String CONTENT_TYPE = "application/json";
  private static final String TOKEN_KEY = "tenant_access_token";
  private static final String AUTH_PREFIX = "Bearer ";
  private static volatile String token = "";
  private final Object LOCK = new Object();
  private Gson gson = new Gson();

  @Value("${feishu.app.id}")
  private String appId;

  @Value("${feishu.app.secret}")
  private String appSecret;

  @Value("${feishu.chat.id}")
  private String chatId;

  @Value("${feishu.msg.type}")
  private String msgType;

  @Resource
  private MifiHttpClient mifiHttpClient;


  @Override
  public Response sendNotice(FeiShuNoticeParam feiShuNoticeParam) {
    freshToken();
    Response result = new Response(true);
    Map<String, String> headers = Maps.newHashMap();
    headers.put("Content-Type", CONTENT_TYPE);
    headers.put("Authorization", AUTH_PREFIX + token);
    HttpEntity entity = new StringEntity(gson.toJson(feiShuNoticeParam), ContentType.APPLICATION_JSON);

    MifiHttpClient.PostParam postParam = new MifiHttpClient.PostParam();
    postParam.setUrl(SEND_URL);
    postParam.setHeaders(headers);
    postParam.setEntity(entity);

    String response = mifiHttpClient.post(postParam);
    log.info("send feishu notice param {},result {}", gson.toJson(feiShuNoticeParam), response);
    Map<String, String> res = gson.fromJson(response, Map.class);
    int code = MapUtils.getIntValue(res, "code", -1);
    if (code == 99991663) {
      fetchToken();
      return sendNotice(feiShuNoticeParam);
    }
    if (code != 0) {
      return new Response(false, "send feishu notice error");
    }
    return result;
  }

  @Override
  public Response getGroupId(String groupName) {
    Response response = new Response(false);
    Map<String, String> headers = Maps.newHashMap();
    headers.put("Content-Type", CONTENT_TYPE);
    headers.put("Authorization", AUTH_PREFIX + token);
    Map<String, String> params = new HashMap<>();
    params.put("page_size", "200");
    MifiHttpClient.GetParam getParam = new MifiHttpClient.GetParam();
    getParam.setUrl(GROUP_URL);
    getParam.setHeaders(headers);
    getParam.setParams(params);
    String httpResponse = mifiHttpClient.get(getParam);
    FeiShuGroupResponse feiShuGroupResponse = gson.fromJson(httpResponse, FeiShuGroupResponse.class);
    if (feiShuGroupResponse.getCode() != 0) {
      response.setDesc("httpResponse fail.");
      return response;
    }
    for (FeiShuGroup group : feiShuGroupResponse.getData().getGroups()) {
      if (group.getName().equals(groupName)) {
        response.setData(group.getChat_id());
        response.setSuccess(true);
        return response;
      }
    }
    return response;
  }

  private void freshToken() {
    if (StringUtils.isEmpty(token)) {
      synchronized (LOCK) {
        if (StringUtils.isEmpty(token)) {
          fetchToken();
        }
      }
    }
  }

  private void fetchToken() {
    Map<String, String> headers = Maps.newHashMap();
    headers.put("Content-Type", CONTENT_TYPE);
    FeiShuTokenParam tokenParam = new FeiShuTokenParam();
    tokenParam.setApp_id(appId);
    tokenParam.setApp_secret(appSecret);
    StringEntity entity = new StringEntity(gson.toJson(tokenParam), ContentType.APPLICATION_JSON);

    MifiHttpClient.PostParam postParam = new MifiHttpClient.PostParam();
    postParam.setUrl(TOKEN_URL);
    postParam.setHeaders(headers);
    postParam.setEntity(entity);

    String response = mifiHttpClient.post(postParam);
    log.info("get feishu token  result {}", response);
    Map<String, String> res = gson.fromJson(response, Map.class);
    token = res.get(TOKEN_KEY);
    if (StringUtils.isEmpty(token)) {
      log.error("get feishu token error,http response: {}" + response);
      throw new RuntimeException("get feishu token error");
    }
  }

  private String content = "{“dashboardId”:1,“evalMatches”:[{“metric”:“High value”,“tags”:null,“value”:100},{“metric”:“Higher Value”,“tags”:null,“value”:200}],“imageUrl”:” https://grafana.com/assets/img/blog/mixed_styles.png”,“message”:“Someone is testing the alert notification within Grafana.”,“orgId”:0,“panelId”:1,“ruleId”:0,“ruleName”:“Test notification”,“ruleUrl”:" +
      "” http://localhost:3000/“,“state”:“alerting”,“tags”:{},“title”:“[Alerting] Test notification”}";

  public Response sendNotice(String alertContent) {
    FeiShuNoticeParam noticeParam = new FeiShuNoticeParam();
    noticeParam.setChat_id(chatId);
    noticeParam.setMsg_type(msgType);
    noticeParam.setUpdate_multi(true);
    noticeParam.setCard(generateFeiShuMsgCard("[Alerting] Test notification", alertContent,
        "http://localhost:3000/",
        "http://localhost:3000/", "", null, ""));
    return sendNotice(noticeParam);
  }

  private FeishuCard generateFeiShuMsgCard(String headTitle, String content, String handlerUrl, String viewUrl, String id, String claim, String groupList) {
    FeishuCard feishuCard = new FeishuCard();
    feishuCard.setConfig(new CardConfig());
    CardHeader header = new CardHeader();
    Map<String, String> cardTitle = new HashMap<>();
    cardTitle.put("tag", "plain_text");
    cardTitle.put("content", headTitle);
    header.setTitle(cardTitle);
    feishuCard.setHeader(header);
    List<Object> elements = new ArrayList<>();
    Map<String, Object> testElement = new HashMap<>();
    CardText cardText = new CardText("plain_text", content);
    testElement.put("tag", "div");
    testElement.put("text", cardText);
    elements.add(testElement);

    Map<String, Object> actionElement = new HashMap<>();
    actionElement.put("tag", "action");

    List<CardAction> actionList = new ArrayList<>();
    CardAction viewAction = formJumpAction("视图", viewUrl);
    actionList.add(viewAction);

    CardAction claimAction = new CardAction();
    claimAction.setType("primary");
    claimAction.setTag("button");
    if (claim == null) {
      claimAction.setText(new HashMap<String, String>(2) {{
        put("tag", "plain_text");
        put("content", "认领");
      }});
      claimAction.setValue(new HashMap<String, String>(6) {{
        put("type", "claim");
        put("id", id);
        put("title", headTitle);
        put("content", content);
        put("handlerUrl", handlerUrl);
        put("viewUrl", viewUrl);
        put("groupList", groupList);
      }});
    } else {
      claimAction.setText(new HashMap<String, String>(2) {{
        put("tag", "plain_text");
        put("content", claim);
      }});
      CardAction handleAction = formJumpAction("处理", handlerUrl);
      actionList.add(handleAction);
    }
    actionList.add(claimAction);
    actionElement.put("actions", actionList);
    elements.add(actionElement);
    feishuCard.setElements(elements);
    return feishuCard;
  }

  private CardAction formJumpAction(String name, String url) {
    CardAction action = new CardAction();
    action.setType("primary");
    action.setTag("button");
    action.setText(new HashMap<String, String>(2) {{
      put("tag", "plain_text");
      put("content", name);
    }});
    action.setUrl(url);
    return action;
  }
}
