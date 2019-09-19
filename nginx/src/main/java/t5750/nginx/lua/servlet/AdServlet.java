package t5750.nginx.lua.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.druid.pool.DruidDataSource;

public class AdServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		Long id = Long.valueOf(idStr);
		// 1、读取Mysql获取数据
		String content = null;
		try {
			content = queryDB(id);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		if (content != null) {
			// 2.1、如果获取到，异步写Redis
			asyncSetToRedis(idStr, content);
			// 2.2、如果获取到，把响应内容返回
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("content-type", "text/html;charset=UTF-8");
			resp.getWriter().write(content);
		} else {
			// 2.3、如果获取不到，返回404状态码
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private DruidDataSource datasource = null;
	private JedisPool jedisPool = null;
	{
		datasource = new DruidDataSource();
		datasource
				.setUrl("jdbc:mysql://192.168.1.110:1234/nginx-lua-web?useUnicode=true&characterEncoding=utf-8&autoReconnect=true");
		datasource.setUsername("root");
		datasource.setPassword("123456");
		datasource.setMaxActive(100);
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(100);
		jedisPool = new JedisPool(poolConfig, "192.168.1.110", 1111);
	}

	private String queryDB(Long id) throws Exception {
		Connection conn = null;
		try {
			conn = datasource.getConnection();
			String sql = "select content from ad where sku_id = ?";
			PreparedStatement psst = conn.prepareStatement(sql);
			psst.setLong(1, id);
			ResultSet rs = psst.executeQuery();
			String content = null;
			if (rs.next()) {
				content = rs.getString("content");
			}
			rs.close();
			psst.close();
			return content;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	private void asyncSetToRedis(final String id, final String content) {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				Jedis jedis = null;
				try {
					jedis = jedisPool.getResource();
					// 5分钟
					jedis.setex(id, 5 * 60, content);
				} catch (Exception e) {
					e.printStackTrace();
					jedisPool.returnBrokenResource(jedis);
				} finally {
					jedisPool.returnResource(jedis);
				}
			}
		});
	}
}
