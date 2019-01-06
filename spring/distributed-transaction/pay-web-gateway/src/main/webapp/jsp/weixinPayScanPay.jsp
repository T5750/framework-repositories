<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html>
<head>
    <title>微支付</title>
    <%--<script type="text/javascript" src="${path}js/jquery-1.7.2.min.js"></script>--%>
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <%--<script type="text/javascript" src="${path}js/jquery.qrcode.min.js"></script>--%>
    <script src="https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
    <link href="${path}/pay_files/pay.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        var times = 280;
        /*第一次读取最新通知*/
        setTimeout(function() {
                    Push();
                },
                2000);
        /*1轮询读取函数*/
        setInterval(function() {
                    Push();
                },
                1000);

        /*请求函数的ajax*/
        function Push() {
            if(times <= 0){
                return;
            }
            times--;
            var queryUrl = $("#queryUrl").val();
            $.ajax({
                type: "GET",
                dataType : "json",
                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                data:{},
                url: queryUrl,
                //请求成功完成后要执行的方法
                success: function(result){
                    if("YES" == result.status){
                        times = 0;
                        $("#weixinDiv").css("display","none");//隐藏
                        $("#sucDiv").css("display","block");//显示
                        $("#returnMerchnatA").attr("href",result.returnUrl);
                        jump(5,result.returnUrl);
                    }
                },
                error : function(data) {
                    console.log("系统异常！" + data)
                }
            });
        }

        function jump(count , surl) {
            window.setTimeout(function(){
                count--;
                if(count > 0) {
                    $('#tiaoSpan').attr('innerHTML', count);
                    jump(count,surl);
                } else {
                    location.href=surl;
                }
            }, 1000);
        }
    </script>
</head>
<body>
<div class="wrap_header">
    <div class="header clearfix">
        <div class="logo_panel clearfix">
            <div class="logo fl"><img src="${path}pay_files/logo.png" alt="logo"></div>
            <div class="lg_txt">| 收银台</div>
        </div>
        <div class="fr tip_panel">
            <div class="txt">欢迎使用扫码支付</div>
            <a href="">常见问题</a>
        </div>
    </div>
</div>
<input type="hidden" id="queryUrl" value="${queryUrl}">
<input type="hidden" id="codeUrl" value="${codeUrl}">
<div id="box" class="">
    <!-- 扫码 -->
    <div class="x-main">
        <div class="pro"><span>${productName}</span><span>应付金额 <b>￥<fmt:parseNumber type="number" pattern="#,#00.0#">${orderPrice}</fmt:parseNumber></b></span></div>
        <div class="weixin" id="weixinDiv">
            <div class="x-left"><img src="${path}pay_files/weixin.jpg" alt="微信导航图"></div>
            <div class="x-right">
                <div class="saoma_panel">
                    <h4>使用微信扫一扫即可付款</h4>
                    <p class="tip">提示:支付成功前请勿手动关闭页面</p>
                    <div class="er" id="code" oid="4835a85a4e01402aa17f8a73c356f80d"></div>
                    <p class="tipa">二维码两小时内有效，请计算扫码支付</p>
                </div>
            </div>
        </div>
    </div>
    <div class="suc_panel" id="sucDiv">
        <div class="hd">支付成功</div>
        <div class="txt"><span id="tiaoSpan">5</span>s后将为你<a id="returnMerchnatA" href="">返回商家</a></div>
    </div>
</div>
<div class="footer w100">
    <div class="container">
        <ul class="con-content">
            <li><br/></li>
        </ul>
        <ul class="con-content">
        </ul>
        <ul class="con-content">
        </ul>
    </div>
    <div class="copyright">Copyright &copy; 2018-2019 <a style="TEXT-DECORATION:none;" href="https://github.com/T5750/framework-repositories/tree/master/spring/distributed-transaction" target="_blank">微服务架构的分布式事务解决方案</a></div>
</div>
  <script type="text/javascript">
      $(function(){
          var str = $("#codeUrl").val();
          $("#code").qrcode({
              render: "table",
              width: 190,
              height:190,
              text: str
          });
      })
  </script>
</body>
</html>