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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>扫码支付</title>
    <meta charset="utf-8">
    <link href="${path}pay_files/pay.css" rel="stylesheet" type="text/css" />
    <%--<script type="text/javascript" src="${path}js/jquery-1.7.2.min.js"></script>--%>
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script language="javascript">
        $(document).ready(function() {
            function jump(count) {
                window.setTimeout(function(){
                    count--;
                    if(count > 0) {
                        $('#tiaoSpan').attr('innerHTML', count);
                        jump(count);
                    } else {
                        var returnUlr = $('#codeUrl').val();
                        location.href=returnUlr;
                    }
                }, 1000);
            }
            jump(3);
        });
    </script>
</head>
<body>
    <div class="wrap_header">
        <div class="header clearfix">
            <div class="logo_panel clearfix">
                <div class="logo fl"><img src="${path}pay_files/logo.png" alt=""></div>
                <div class="lg_txt">| 收银台</div>
            </div>
            <div class="fr tip_panel">
                <div class="txt">欢迎使用扫码支付</div>
                <a href="">常见问题</a>
            </div>
        </div>
    </div>
    <div id="box" class="">
        <!-- 扫码 -->
        <div class="x-main">
            <div class="pro"><span>${scanPayByResult.productName}</span><span>应付金额 <b>￥<fmt:parseNumber type="number" pattern="#,#00.0#">${scanPayByResult.orderPrice}</fmt:parseNumber></b></span></div>
        </div>
        <div class="suc_panel_result">
            <div class="hd">支付成功</div>
            <div class="txt"><span id="tiaoSpan">5</span>s后将为你<a href="${scanPayByResult.returnUrl}">返回商家</a></div>
            <input type="hidden" id="codeUrl" value="${scanPayByResult.returnUrl}">
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
</body>
</html>
