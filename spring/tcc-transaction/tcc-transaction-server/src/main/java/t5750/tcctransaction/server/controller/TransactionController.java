package t5750.tcctransaction.server.controller;

import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import t5750.tcctransaction.server.dao.TransactionDao;
import t5750.tcctransaction.server.vo.CommonResponse;
import t5750.tcctransaction.server.vo.TransactionVo;

/**
 */
@Controller
public class TransactionController {
	@Autowired
	@Qualifier("jdbcTransactionDao")
	private TransactionDao transactionDao;
	@Value("${tcc_domain}")
	private String tccDomain;
	public static final Integer DEFAULT_PAGE_NUM = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;

	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public ModelAndView manager() {
		ModelAndView modelAndView = new ModelAndView("manager");
		return modelAndView;
	}

	@RequestMapping(value = "/management/domain/{domain}", method = RequestMethod.GET)
	public ModelAndView manager(@PathVariable String domain) {
		return manager(domain, DEFAULT_PAGE_NUM);
	}

	@RequestMapping(value = "/management/domain/{domain}/pagenum/{pageNum}", method = RequestMethod.GET)
	public ModelAndView manager(@PathVariable String domain,
			@PathVariable Integer pageNum) {
		ModelAndView modelAndView = new ModelAndView("manager");
		List<TransactionVo> transactionVos = transactionDao.findTransactions(
				domain, pageNum, DEFAULT_PAGE_SIZE);
		Integer totalCount = transactionDao.countOfFindTransactions(domain);
		Integer pages = totalCount / DEFAULT_PAGE_SIZE;
		if (totalCount % DEFAULT_PAGE_SIZE > 0) {
			pages++;
		}
		modelAndView.addObject("transactionVos", transactionVos);
		modelAndView.addObject("pageNum", pageNum);
		modelAndView.addObject("pageSize", DEFAULT_PAGE_SIZE);
		modelAndView.addObject("pages", pages);
		modelAndView.addObject("domain", domain);
		// "/manager/domain/" 改成 "/management/domain/" ，要不然分页路径会失败 （吴水成）
		modelAndView.addObject("urlWithoutPaging", tccDomain
				+ "/management/domain/" + domain);
		return modelAndView;
	}

	@RequestMapping(value = "/domain/{domain}/retry/reset", method = RequestMethod.PUT)
	@ResponseBody
	public CommonResponse<Void> reset(@PathVariable String domain,
			String globalTxId, String branchQualifier) {
		transactionDao.resetRetryCount(domain,
				DatatypeConverter.parseHexBinary(globalTxId),
				DatatypeConverter.parseHexBinary(branchQualifier));
		return new CommonResponse<Void>();
	}
}
