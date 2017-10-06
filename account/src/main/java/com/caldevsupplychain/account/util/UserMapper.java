package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.ws.account.UserWS;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper extends ConfigurableMapper {

	@Override
	protected void configure(MapperFactory factory) {

		// WS to Bean: field is (Bi-directional)
		factory.classMap(UserWS.class, UserBean.class)
				.fieldAToB("role", "roles{}")
				.customize(new CustomMapper<UserWS, UserBean>() {
					@Override
					public void mapAtoB(UserWS userWS, UserBean userBean, MappingContext context) {
						userBean.setRoles(Lists.newArrayList(new Role(userWS.getRole())));
					}
				})
				.constructorA()
				.constructorB()
				.byDefault()
				.register();


		// Entity to Bean
		factory.classMap(User.class, UserBean.class)
				.field("roles{}", "roles{}")
				.customize(new CustomMapper<User, UserBean>() {
					@Override
					public void mapAtoB(User user, UserBean userBean, MappingContext context) {
						userBean.setRoles(user.getRoles());
					}
				})
				.constructorA()
				.constructorB()
				.byDefault()
				.register();




		// Bean <> WS
		// TODO: buggy here
//		factory.classMap(UserBean.class, UserWS.class)
//				.fieldAToB("roles{name}", "role")
//				.customize(new CustomMapper<UserBean, UserWS>() {
//					@Override
//					public void mapAtoB(UserBean userBean, UserWS userWS, MappingContext context) {
//
//					}
//				})
//				.constructorA()
//				.constructorB()
//				.byDefault()
//				.register();

	}
}
