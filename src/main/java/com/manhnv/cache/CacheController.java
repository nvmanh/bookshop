package com.manhnv.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.common.PathConsts;
import com.manhnv.service.CachingService;

@RestController
public class CacheController {

	@Autowired
	CachingService cachingService;

	@GetMapping(path = PathConsts.v1.CLEAR_CACHE)
	public void clearAllCaches() {
		cachingService.evictAllCaches();
	}
}
