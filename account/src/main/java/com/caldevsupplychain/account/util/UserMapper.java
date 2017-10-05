package com.caldevsupplychain.account.util;

import org.springframework.stereotype.Component;

import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.ws.account.UserWS;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class UserMapper extends ConfigurableMapper {
	@Override
	protected void configure(MapperFactory factory) {
		// Entity to Bean
		factory.classMap(User.class, UserBean.class)
			.constructorA()
			.constructorB()
			.byDefault()
			.register();

		// Bean to WS
		factory.classMap(UserBean.class, UserWS.class).constructorA()
			.constructorB()
			.byDefault()
			.register();
	}
}
