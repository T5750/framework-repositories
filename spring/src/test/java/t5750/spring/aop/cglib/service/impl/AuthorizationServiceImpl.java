package t5750.spring.aop.cglib.service.impl;

import java.lang.reflect.Method;

import t5750.spring.aop.cglib.service.AuthorizationService;

public class AuthorizationServiceImpl implements AuthorizationService {
	@Override
	public void authorize(Method method) {
	}
}