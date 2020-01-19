package com.mgp.ddemo;

import com.mgp.ddemo.commons.netty4.oldserver.EchoServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mgp"})
@MapperScan("com.mgp.ddemo.*.dao")
@EnableAsync
@EnableScheduling
public class DdemoApplication extends SpringBootServletInitializer implements CommandLineRunner {
	/**
	 * 手动部署命令：
	 * java -jar ddemo.jar 前台指执行，方便查看日志
	 * java -jar ddemo.jar &   后台执行访问会被唤醒
	 * nohup java -jar ddemo.jar & 后台执行，日志默认输入当前目录下的nohup.out文件中
	 * nohup java -jar xxx.jar > catalina.out  2>&1 &  后台执行，日志输入当前目录下的catalina.out文件中
	 * nohup java -jar xxx.jar >/dev/null &   后台执行，没有日志
	 * docker部署：(pom文件两种不同的方式：https://www.cnblogs.com/jpfss/p/10945324.html)
	 * Linux服务器安装doker与项目依赖环境
	 * 配置docker文件，开启防火墙端口2375，重启docker服务或服务器(https://blog.csdn.net/qq_36850813/article/details/89924207)
	 * idea安装docker插件(Docker integration)
	 * mvn clean package docker:build -DpushImage
	 * 项目push后需要手动创建实例：docker run --name ddemo -d -p8177:8012 ddemo/ddemo ddemo
	 */
	@Value("${netty.port}")
	private int port;

	public static void main(String[] args) {
		System.out.println("project start");
		SpringApplication.run(DdemoApplication.class, args);
	}

	@Override//为了打包springboot项目
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		new EchoServer(port).start();
	}
}
