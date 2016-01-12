package com.hiersun.jewelry.api.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface RedisBaseService {

	/**
	 * 向名称为key的set中添加元素member
	 */
	public long sadd(String key,String value);
	/**
	 * 测试value是否是名称为key的set的元素
	 * @param key
	 * @return
	 */
	public boolean sismember(String key,String value);

	/**
	 * 设置一个key的过期时间（单位：秒）
	 * 
	 * @param key
	 *            key值
	 * @param seconds
	 *            多少秒后过期
	 * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
	 */
	public long expire(String key, int seconds);

	/**
	 * 设置一个key在某个时间点过期
	 * 
	 * @param key
	 *            key值
	 * @param unixTimestamp
	 *            unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
	 * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
	 */
	public long expireAt(String key, int unixTimestamp);

	/**
	 * 截断一个List
	 * 
	 * @param key
	 *            列表key
	 * @param start
	 *            开始位置 从0开始
	 * @param end
	 *            结束位置
	 * @return 状态码
	 */
	public String trimList(String key, long start, long end);

	/**
	 * 检查Set长度
	 * 
	 * @param key
	 * @return
	 */
	public long countSet(String key);

	/**
	 * 添加到Set中（同时设置过期时间）
	 * 
	 * @param key
	 *            key值
	 * @param seconds
	 *            过期时间 单位s
	 * @param value
	 * @return
	 */
	public boolean addSet(String key, int seconds, String... value);

	/**
	 * 添加到Set中
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addSet(String key, String... value);

	/**
	 * @param key
	 * @param value
	 * @return 判断值是否包含在set中
	 */
	public boolean containsInSet(String key, String value);

	/**
	 * 获取Set
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	public String get(String key, String defaultValue);

	/**
	 * 获取Set
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getObject(String key);

	/**
	 * 获取Set
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getSet(String key);

	/**
	 * 从set中删除value
	 * 
	 * @param key
	 * @return
	 */
	public boolean removeSetValue(String key, String... value);

	/**
	 * 从list中删除value 默认count 1
	 * 
	 * @param key
	 * @param values
	 *            值list
	 * @return
	 */
	public int removeListValue(String key, List<String> values);

	/**
	 * 从list中删除value
	 * 
	 * @param key
	 * @param count
	 * @param values
	 *            值list
	 * @return
	 */
	public int removeListValue(String key, long count, List<String> values);

	/**
	 * 从list中删除value
	 * 
	 * @param key
	 * @param count
	 *            要删除个数
	 * @param value
	 * @return
	 */
	public boolean removeListValue(String key, long count, String value);

	/**
	 * 截取List
	 * 
	 * @param key
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public List<String> rangeList(String key, long start, long end);

	/**
	 * 检查List长度
	 * 
	 * @param key
	 * @return
	 */
	public long countList(String key);

	/**
	 * 添加到List中（同时设置过期时间）
	 * 
	 * @param key
	 *            key值
	 * @param seconds
	 *            过期时间 单位s
	 * @param value
	 * @return
	 */
	public boolean addList(String key, int seconds, String... value);

	/**
	 * 添加到List
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addList(String key, String... value);

	/**
	 * 添加到List(只新增)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addList(String key, List<String> list);

	/**
	 * 获取List
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getList(String key);

	/**
	 * 设置HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @param value
	 *            Json String or String value
	 * @return
	 */
	public boolean setHSet(String domain, String key, String value);

	/**
	 * 获得HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return Json String or String value
	 */
	public String getHSet(String domain, String key);

	/**
	 * 删除HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return 删除的记录数
	 */
	public long delHSet(String domain, String key);

	/**
	 * 删除HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return 删除的记录数
	 */
	public long delHSet(String domain, String... key);

	/**
	 * 判断key是否存在
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return
	 */
	public boolean existsHSet(String domain, String key);

	/**
	 * 返回 domain 指定的哈希集中所有字段的value值
	 *
	 * @param domain
	 * @return
	 */

	public List<String> hvals(String domain);

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 *
	 * @param domain
	 * @return
	 */

	public Set<String> hkeys(String domain);

	/**
	 * 返回 domain 指定的哈希key值总数
	 *
	 * @param domain
	 * @return
	 */
	public long lenHset(String domain);

	/**
	 * 设置排序集合
	 *
	 * @param key
	 * @param score
	 * @param value
	 * @return
	 */
	public boolean setSortedSet(String key, long score, String value);

	/**
	 * 获得排序集合
	 *
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @param orderByDesc
	 * @return
	 */
	public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc);

	/**
	 * 计算排序长度
	 *
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @return
	 */
	public long countSoredSet(String key, long startScore, long endScore);

	/**
	 * 删除排序集合
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delSortedSet(String key, String value);

	/**
	 * 获得排序集合
	 *
	 * @param key
	 * @param startRange
	 * @param endRange
	 * @param orderByDesc
	 * @return
	 */
	public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc);

	/**
	 * 获得排序打分
	 *
	 * @param key
	 * @return
	 */
	public Double getScore(String key, String member);

	/**
	 * @Title setObject<br>
	 * @Description 添加（序列化）对象<br>
	 * @param key
	 * @param value
	 * <br>
	 * @return boolean 返回类型<br>
	 * @throws<br>
	 * 
	 * @author L.L<br>
	 * @date 2015年5月10日 下午6:33:18<br>
	 */
	public boolean setObject(String key, Serializable value);

	/**
	 * @Title setObject<br>
	 * @Description 添加（序列化）对象<br>
	 * @param key
	 * @param value
	 * <br>
	 * @return boolean 返回类型<br>
	 * @throws<br>
	 * 
	 * @author L.L<br>
	 * @date 2015年5月10日 下午6:33:18<br>
	 */
	public boolean setObject(String key, int second, Serializable value);

	/**
	 * @Title set<br>
	 * @param key
	 *            key值
	 * @param seconds
	 *            过期时间 单位s
	 * @param value
	 * 
	 * @return boolean 返回类型<br>
	 * @throws<br>
	 * 
	 * @author L.L<br>
	 * @date 2015年5月10日 下午6:38:16<br>
	 */
	public boolean set(String key, int second, String value);

	public boolean set(String key, String value);

	public boolean del(String key);

	public long incr(String key);

	public long decr(String key);

	/**
	 * 赋值
	 * 
	 * @param key
	 * @param obj
	 * @return boolean
	 * @throws
	 * 
	 * @author Feng Feihu
	 */
	public boolean setObj(String key, Object obj);

	/**
	 * 取值
	 * 
	 * @param key
	 * @return Object
	 * @throws
	 * 
	 * @author Feng Feihu
	 */
	public Object getObj(String key);

}
