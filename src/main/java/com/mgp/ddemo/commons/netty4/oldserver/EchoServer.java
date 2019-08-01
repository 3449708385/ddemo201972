package com.mgp.ddemo.commons.netty4.oldserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 回声服务器
 * 参考资料《Netty实战精髓》：https://waylau.com
 * @author wusq
 * @date 2019/4/19
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port = port;
    }


    public void start() throws Exception{
        // 监听线程组，监听客户端请求
        NioEventLoopGroup acceptorGroup = new NioEventLoopGroup();
        // 处理客户端相关操作线程组，负责处理与客户端的数据通讯
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        // 服务启动相关配置信息
        ServerBootstrap strap = new ServerBootstrap();
        try {
            // 绑定线程组
            strap.group(acceptorGroup, clientGroup);
            // 设定通讯模式为NIO
            strap.channel(NioServerSocketChannel.class);
            // 设定缓冲区大小
            strap.option(ChannelOption.SO_BACKLOG, 1024);
            // SO_SNDBUF发送缓冲区，SO_RCVBUF接收缓冲区，
            // SO_KEEPALIVE开启心跳监测（保证连接有效）,TCP_NODELAY低延时
            // CONNECT_TIMEOUT_MILLIS 客户端配置超时时间,服务器端不用
            strap.localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel sc) throws Exception{
                            // 数据分隔符, 定义的数据分隔符一定是一个ByteBuf类型的数据对象。
                            ByteBuf delimiter = Unpooled.copiedBuffer("$E$".getBytes());
                            ChannelHandler[] acceptorHandlers = new ChannelHandler[3];
                            // 处理固定结束标记符号的Handler。这个Handler没有@Sharable注解修饰，
                            // 必须每次初始化通道时创建一个新对象
                            // 使用特殊符号分隔处理数据粘包问题，也要定义每个数据包最大长度。netty建议数据有最大长度。
                            acceptorHandlers[0] = new DelimiterBasedFrameDecoder(1024, delimiter);
                            // 字符串解码器Handler，会自动处理channelRead方法的msg参数，将ByteBuf类型的数据转换为字符串对象
                            acceptorHandlers[1] = new StringDecoder(Charset.forName("UTF-8"));
                            acceptorHandlers[2] = new EchoServerHandler();
                            sc.pipeline().addLast(acceptorHandlers);
                        }
                    });
            ChannelFuture future = strap.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } finally {
            acceptorGroup.shutdownGracefully().sync();
            clientGroup.shutdownGracefully().sync();
        }
    }

   /* public static void main(String[] args) throws Exception{
        new EchoServer(8888).start();
    }*/
}
