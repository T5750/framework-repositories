package t5750.pay.common.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;

/**
 * @类功能说明： 数据访问层基础支撑接口. @版本：V1.0
 */
public interface BaseDao<T> {
	/**
	 * 函数功能说明 ：单条插入数据.
	 *
	 * @参数：@param entity @参数：@return @return：int @throws
	 */
	int insert(T entity);

	/**
	 * 函数功能说明 ： 批量插入数据.
	 *
	 * @参数：@param list @参数：@return @return：int @throws
	 */
	int insert(List<T> list);

	/**
	 * 函数功能说明 ：根据id单条更新数据.
	 *
	 * @参数：@param entity @参数：@return @return：int @throws
	 */
	int update(T entity);

	/**
	 * 函数功能说明 ： 根据id批量更新数据.
	 *
	 * @参数：@param list @参数：@return @return：int @throws
	 */
	int update(List<T> list);

	/**
	 * 函数功能说明 ： 根据column批量更新数据.
	 *
	 * @参数：@param paramMap @参数：@return @return：int @throws
	 */
	int update(Map<String, Object> paramMap);

	/**
	 * 函数功能说明 ： 根据id查询数据.
	 *
	 * @参数：@param id
	 * @参数：@return
	 * @return：T @throws
	 */
	T getById(String id);

	/**
	 * 函数功能说明 ： 根据column查询数据.
	 *
	 * @参数：@param paramMap
	 * @参数：@return
	 * @return：T @throws
	 */
	public T getByColumn(Map<String, Object> paramMap);

	/**
	 * 根据条件查询 listBy: <br/>
	 *
	 * @param paramMap
	 * @return 返回实体
	 */
	public T getBy(Map<String, Object> paramMap);

	/**
	 * 根据条件查询列表数据.
	 */
	public List<T> listBy(Map<String, Object> paramMap);

	/**
	 * 函数功能说明 ： 根据column查询列表数据.
	 *
	 * @参数：@param paramMap @参数：@return @return：List<T> @throws
	 */
	public List<T> listByColumn(Map<String, Object> paramMap);

	/**
	 * 函数功能说明 ： 根据column查询记录数.
	 *
	 * @参数：@param paramMap @参数：@return @return：Long @throws
	 */
	Long getCountByColumn(Map<String, Object> paramMap);

	/**
	 * 函数功能说明 ： 根据id删除数据.
	 *
	 * @参数：@param id @参数：@return @return：int @throws
	 */
	int delete(String id);

	/**
	 * 函数功能说明 ： 根据id批量删除数据.
	 *
	 * @参数：@param list @参数：@return @return：int @throws
	 */
	int delete(List<T> list);

	/**
	 * 函数功能说明 ： 根据column批量删除数据.
	 *
	 * @参数：@param paramMap @参数：@return @return：int @throws
	 */
	int delete(Map<String, Object> paramMap);

	/**
	 * 函数功能说明 ： 分页查询数据 .
	 *
	 * @参数：@param pageParam @参数：@param
	 *            paramMap @参数：@return @return：PageBean @throws
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);

	public SqlSessionTemplate getSessionTemplate();

	public SqlSession getSqlSession();
}
