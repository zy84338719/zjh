package com.zttx.service.strength;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.zttx.po.User;

@Service(value="userServiceStrength")
public class UserServiceStrength {
	
	public void beforeLogin(){
		System.out.println("进入login...");
	}
	
	public void afterLogin(){
		System.out.println("结束login...");
	}
	
	public User inLogin(ProceedingJoinPoint point) throws Throwable{
		System.out.println("环绕增强前...");
		
        Object[] obj = point.getArgs() ;  
        Object retVal = null ;  
      
        retVal = point.proceed(obj);  
          
		
		System.out.println("环绕增强后...");
		
		return (User) retVal;
	}
}
