package com.mqld.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mqld.annotation.FireAuthority;
import com.mqld.constant.Constant;
import com.mqld.enums.AuthorityType;
import com.mqld.enums.ResultType;
import com.mqld.model.User;
import com.mqld.util.JsonUtil;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handler2=(HandlerMethod) handler;
        FireAuthority fireAuthority = handler2.getMethodAnnotation(FireAuthority.class);

        if(null == fireAuthority){
            return true;
        }

        logger.debug("fireAuthority", fireAuthority.toString());

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        boolean flag = false;
        if (Constant.ADMIN.equals(user.getAuthority())) {
			flag=true;
		}else {
			for(AuthorityType at:fireAuthority.authorityTypes()){
	            if(user.getAuthority().equals(at.getName())){
	                flag = true;
	                break;
	            }
	        }
		}
        
        
        if(false == flag){

            if (fireAuthority.resultType() == ResultType.page) {
            	 StringBuilder sb = new StringBuilder();
                 sb.append(request.getContextPath());
                 sb.append("/main.action");
                 response.sendRedirect(sb.toString());
            } else if (fireAuthority.resultType() == ResultType.json) {
            	JsonUtil.flushError(response, "无此权限");
            }
        }
       

        return flag;
    }

}