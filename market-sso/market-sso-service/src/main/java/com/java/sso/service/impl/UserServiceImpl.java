package com.java.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import com.java.constant.Constant;
import com.java.dto.MarketResult;
import com.java.mapper.TbUserMapper;
import com.java.model.TbUser;
import com.java.model.TbUserExample;
import com.java.model.TbUserExample.Criteria;
import com.java.redis.JedisClient;
import com.java.sso.service.UserServiceI;
import com.java.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserServiceI {

	@Resource
	private TbUserMapper tbUserMapper;

	@Resource
	private JedisClient jedisClient;

	@Value("${REDIS_TOKEN}")
	private String REDIS_TOKEN;

	@Value("${TOKEN_EXPIRE}")
	private int TOKEN_EXPIRE;

	@Override
	public MarketResult checkData(String param, int type) {
		if (StringUtils.isBlank(param)) {
			return MarketResult.build(400, "数据异常");
		}
		return checkUserData(param, type) ? MarketResult.ok(true) : MarketResult.ok(false);
	}

	private boolean checkUserData(String param, int type) {
		boolean flag = true;
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		switch (type) {
		case Constant.USER_EMAIL:
			// 正则校验email格式
			criteria.andEmailEqualTo(param);
			break;
		case Constant.USER_PHONE:
			// 正则校验手机
			criteria.andPhoneEqualTo(param);
			break;
		case Constant.USER_USERNAME:
			// 校验用户名
			criteria.andUsernameEqualTo(param);
			break;
		default:
			// type不存在返回false
			flag = false;
			break;
		}
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// list有值
		if (!list.isEmpty()) {
			flag = false;
		}
		return flag;
	}

	@Override
	public MarketResult createUser(TbUser tbUser, String pwdRepeat) {
		if (null == tbUser || StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())
				|| StringUtils.isBlank(tbUser.getPhone()) || StringUtils.isBlank(pwdRepeat)) {
			return MarketResult.build(400, "用户名、密码、验证密码、手机号不能为空");
		}
		if (!checkUserData(tbUser.getUsername(), Constant.USER_USERNAME)) {
			return MarketResult.build(400, "用户名已存在");
		} else if (!checkUserData(tbUser.getPhone(), Constant.USER_PHONE)) {
			return MarketResult.build(400, "手机号码已经被注册");
		} else if (!tbUser.getPassword().equals(pwdRepeat)) {
			return MarketResult.build(400, "两次输入的密码不一致");
		} else {
			tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
			tbUser.setUpdated(new Date());
			tbUser.setCreated(new Date());
			tbUserMapper.insert(tbUser);
		}
		return MarketResult.ok();
	}

	@Override
	public MarketResult userLogin(String userName, String password) {
		//判断是否为空
		if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)) {
			return MarketResult.build(400, "用户名或密码为空");
		}
		TbUserExample example = new TbUserExample();
		// 从数据库找出用户名和密码一致的账户
		example.createCriteria().andUsernameEqualTo(userName)
				.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return MarketResult.build(400, "用户名或密码错误");
		}
		// 获取账户
		TbUser tbUser = list.get(0);
		// 生成token,且这个token要有唯一标识
		String token = UUID.randomUUID().toString();
		// 将密码设成空值，以防存入jedis后被盗取信息
		tbUser.setPassword("");
		// 给token加前缀
		jedisClient.set(REDIS_TOKEN + ":" + token, JsonUtils.objectToJson(tbUser));
		// 设置token的超时时间
		jedisClient.expire(REDIS_TOKEN + ":" + token, TOKEN_EXPIRE);
		// 将token返回前台保存至cookie
		return MarketResult.ok(token);
	}

	@Override
	public MarketResult getUserToken(String token) {
		//先获取token对应的值
		String userToken = jedisClient.get(REDIS_TOKEN+":"+token);
		//如果获取的值为空
		if (!StringUtils.isNotBlank(userToken)) {
			return MarketResult.build(400, "登陆信息过期，请重新登陆");
		}
		//不为空，设置时间 包装成jsonpojo返回
		jedisClient.expire(REDIS_TOKEN + ":" + token, TOKEN_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(userToken, TbUser.class);
		return MarketResult.ok(user);
	}

	@Override
	public MarketResult deleteToken(String token) {
		jedisClient.del(REDIS_TOKEN+":"+token);
		return MarketResult.ok();
	}
}
