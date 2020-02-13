package t5750.springboot.module.miniprogram.service;

import javax.servlet.http.HttpServletRequest;

public interface ApiBackendService {
	String datacube(HttpServletRequest request, String url);
}