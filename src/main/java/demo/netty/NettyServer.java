package demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {

        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup, workerGroup)
            .channel(NioServerSocketChannel.class) //监听端口的socketChannel
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChildChannelHandler())
            ;

        ChannelFuture bindFuture = serverBootstrap.bind(9009).sync();//同步等待启动服务器监控端口
        bindFuture.channel().closeFuture().sync(); //同步等待关闭启动服务器结果

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mainGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyServerHandler());
        }
    }
}
