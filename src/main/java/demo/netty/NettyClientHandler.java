package demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf byteBuf;

    public NettyClientHandler() {
        byte[] bytes = "hello netty".getBytes();
        byteBuf = Unpooled.buffer(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf responses = (ByteBuf) msg;
        byte[] bytes = new byte[responses.readableBytes()];
        responses.readBytes(bytes);

        String data = new String(bytes, "UTF-8");
        System.out.println("received response: " + data);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
