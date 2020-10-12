/**
 * 
 */
package com.labbol.cocoon.plugin.manage.cache.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.CacheManagerFactory;
import org.yelong.core.model.manage.ModelManager;

import com.labbol.cocoon.controller.BaseCocoonController;
import com.labbol.cocoon.msg.JsonMsg;
import com.labbol.core.check.login.LoginValidate;

/**
 * 清空所有的缓存
 * 
 * @since 2.0
 */
@Controller
@LoginValidate(validate = false)
public class ClearAllCacheController extends BaseCocoonController {

	@Resource
	private List<CacheManagerFactory> cacheManagerFactorys;

	@ResponseBody
	@RequestMapping("clearAllCache")
	public String clearAllCache() {
		for (CacheManagerFactory cacheManagerFactory : cacheManagerFactorys) {
			List<CacheManager> cacheManagers = cacheManagerFactory.getHasCreate();
			cacheManagers.forEach(CacheManager::clear);
		}
		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		modelManager.clearCacheModelAndTable();
		return toJson(new JsonMsg(true, "所有缓存已经清除"));
	}

}
