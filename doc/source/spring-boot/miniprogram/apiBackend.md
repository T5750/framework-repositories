# API Backend

## 登录
### auth.code2Session
登录凭证校验。通过 [wx.login](https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html) 接口获得临时登录凭证 `code` 后传到开发者服务器调用此接口完成登录流程。
```
GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
```

## 接口调用凭证
### auth.getAccessToken
获取小程序全局唯一后台接口调用凭据（`access_token`）。**调用绝大多数后台接口时都需使用 `access_token`，开发者需要进行妥善保存。**
```
GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
```

## 数据分析
### analysis.getMonthlyRetain
获取用户访问小程序月留存
```
POST https://api.weixin.qq.com/datacube/getweanalysisappidmonthlyretaininfo?access_token=ACCESS_TOKEN
```

### analysis.getDailySummary
获取用户访问小程序数据概况
```
POST https://api.weixin.qq.com/datacube/getweanalysisappiddailysummarytrend?access_token=ACCESS_TOKEN
```

### analysis.getMonthlyVisitTrend
获取用户访问小程序数据月趋势
```
POST https://api.weixin.qq.com/datacube/getweanalysisappidmonthlyvisittrend?access_token=ACCESS_TOKEN
```

### analysis.getUserPortrait
获取小程序新增或活跃用户的画像分布数据。时间范围支持昨天、最近7天、最近30天。其中，新增用户数为时间范围内首次访问小程序的去重用户数，活跃用户数为时间范围内访问过小程序的去重用户数。
```
POST https://api.weixin.qq.com/datacube/getweanalysisappiduserportrait?access_token=ACCESS_TOKEN
```

### analysis.getVisitDistribution
获取用户小程序访问分布数据
```
POST https://api.weixin.qq.com/datacube/getweanalysisappidvisitdistribution?access_token=ACCESS_TOKEN
```

### analysis.getVisitPage
访问页面。**目前只提供按 page_visit_pv 排序的 top200。**
```
POST https://api.weixin.qq.com/datacube/getweanalysisappidvisitpage?access_token=ACCESS_TOKEN
```

## 小程序码
### wxacode.createQRCode
获取小程序二维码，适用于需要的码数量较少的业务场景。**通过该接口生成的小程序码，永久有效，有数量限制**，详见[获取二维码](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html)。
```
POST https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN
```

## 内容安全
### security.msgSecCheck
检查一段文本是否含有违法违规内容。应用场景举例：
1. 用户个人资料违规文字检测；
2. 媒体新闻类用户发表文章，评论内容检测；
3. 游戏类用户编辑上传的素材(如答题类小游戏用户上传的问题及答案)检测等。 

_频率限制：单个 `appId` 调用上限为 4000 次/分钟，2,000,000 次/天_
```
POST https://api.weixin.qq.com/wxa/msg_sec_check?access_token=ACCESS_TOKEN
```

## 运维中心
### operation.realtimelogSearch
实时日志查询
```
GET https://api.weixin.qq.com/wxaapi/userlog/userlog_search?access_token=ACCESS_TOKEN
```

## References
- [auth.code2Session](https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html)
- [imooc-springboot-wxlogin](https://github.com/leechenxiang/imooc-springboot-wxlogin)