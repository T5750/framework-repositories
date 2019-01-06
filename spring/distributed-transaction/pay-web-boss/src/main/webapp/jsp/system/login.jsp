<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="${baseURL }/common/taglib/taglib.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微支付系统管理后台</title>
<link rel="stylesheet" type="text/css" href="${baseURL }/dwz/themes/css/login.css" />
<script type="text/javascript" language="javascript" src="${baseURL }/common/js/login/login.js"></script>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<%--<h1 class="login_logo">
				<a href="https://github.com/T5750/framework-repositories/tree/master/spring/distributed-transaction" target="_blank"><img src="${baseURL }/dwz/themes/default/images/login_logo.png" /></a>
			</h1>--%>
			<div class="login_headerContent">
				<%--<div class="navList">
					<ul>
						<li><a href="#">设为首页</a></li>
					</ul>
				</div>--%>
				<h2 class="login_title"><img src="${baseURL }/dwz/themes/default/images/login_title.png" /></h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form id="form1" action="${baseURL }/login/index" method="post">
				    <div style="color: red;padding-left: 80px;padding-bottom: 10px;">${message}</div>
					<div class="login_user">
			              <ul>
			                  <li><label>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label></li>
			                  <li>
			                       <input type="text" name="empNo" size="18" class="login_input" value="admin"/>
			                  </li>
			              </ul>
			              <ul>
			                  <li><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label></li>
			                  <li>
			                      <input type="password" name="empPwd" size="18" class="login_input" value="admin"/>
			                  </li>
			              </ul>
			        </div>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<h1 style="font-size: 30px;margin: 10px 0px 10px 0px;">微服务架构的分布式事务解决方案</h1>
			<%--<div class="login_banner"><img src="${baseURL }/dwz/themes/default/images/login_banner.jpg" /></div>--%>
			<div class="login_main">
				<ul class="helpList">
				</ul>
				<div class="login_inner">
				</div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2018-2019 <a style="TEXT-DECORATION:none;" href="https://github.com/T5750/framework-repositories/tree/master/spring/distributed-transaction" target="_blank">微服务架构的分布式事务解决方案</a>
		</div>
	</div>
</body>
</html>