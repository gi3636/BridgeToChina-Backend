package com.btchina.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.btchina.**.mapper"})
public class MybatisPlusDefaultConfig {

	@Bean
	public MybatisPlusInterceptor mybatisPlusDefaultInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		//分页插件
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		//乐观锁插件
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		// 阻止恶意的全表更新删除
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}

}
