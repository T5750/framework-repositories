package t5750.solr.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("solr")
public class SolrController {
	public static final String SOLR_COLLECTION = "collection1";
	@Autowired
	private SolrClient client;

	/**
	 * 新增/修改索引当id存在的时候，此方法是修改(当然，我这里用的uuid，不会存在的)，如果id不存在，则是新增
	 */
	@RequestMapping("add")
	public String add() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", uuid);
			doc.setField("content", "我是中国人，我爱中国");
			/*
			 * 如果spring.data.solr.host里面配置到core了，那么这里就不需要传collection1这个参数，下面都是一样的
			 */
			client.add(SOLR_COLLECTION, doc);
			// client.commit();
			client.commit(SOLR_COLLECTION);
			return uuid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * 根据id查询索引
	 */
	@RequestMapping("getById")
	public String getById(String id) throws Exception {
		SolrDocument document = client.getById(SOLR_COLLECTION, id);
		System.out.println(document);
		return document.toString();
	}

	@RequestMapping("addMobile")
	public String addMobile() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", uuid);
			doc.setField("title", "手机" + uuid);
			float randomFloat = new Random().nextFloat();
			float generatedFloat = randomFloat * 10000;
			doc.setField("price", generatedFloat);
			client.add(SOLR_COLLECTION, doc);
			client.commit(SOLR_COLLECTION);
			return uuid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * 综合查询：在综合查询中，有按条件查询，条件过滤，排序，分页，高亮显示，获取部分域信息
	 */
	@RequestMapping("search")
	public Map<String, Map<String, List<String>>> search() {
		try {
			SolrQuery params = new SolrQuery();
			// 查询条件，这里的q对应下面图片标红的地方
			params.set("q", "手机");
			// 过滤条件
			params.set("fq", "price:[100 TO 100000]");
			// 排序
			params.addSort("price", SolrQuery.ORDER.asc);
			// 分页
			params.setStart(0);
			params.setRows(20);
			// 默认域
			params.set("df", "title");
			// 只查询指定域
			params.set("fl", "id,title,price");
			// 高亮
			// 打开开关
			params.setHighlight(true);
			// 指定高亮域
			params.addHighlightField("title");
			// 设置前缀
			params.setHighlightSimplePre("<span style='color:red'>");
			// 设置后缀
			params.setHighlightSimplePost("</span>");
			QueryResponse queryResponse = client.query(params);
			SolrDocumentList results = queryResponse.getResults();
			long numFound = results.getNumFound();
			System.out.println(numFound);
			// 获取高亮显示的结果，高亮显示的结果和查询结果是分开放的
			Map<String, Map<String, List<String>>> highlight = queryResponse
					.getHighlighting();
			for (SolrDocument result : results) {
				System.out.println(result.get("id"));
				System.out.println(result.get("title"));
				System.out.println(result.get("price"));
				Map<String, List<String>> map = highlight.get(result.get("id"));
				List<String> list = map.get("title");
				System.out.println(list.get(0));
				System.out.println("------------------");
				System.out.println();
			}
			return highlight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据id删除索引
	 */
	@RequestMapping("delete")
	public String delete(String id) {
		try {
			client.deleteById(SOLR_COLLECTION, id);
			client.commit(SOLR_COLLECTION);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * 删除所有的索引
	 */
	@RequestMapping("deleteAll")
	public String deleteAll() {
		try {
			client.deleteByQuery(SOLR_COLLECTION, "*:*");
			client.commit(SOLR_COLLECTION);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
}