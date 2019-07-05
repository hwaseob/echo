package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class client {
    static final Logger log = LoggerFactory.getLogger(client.class);

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
//        EventLoopGroup group = new OioEventLoopGroup();
        try
        {
            log.info("start");
            ChannelFuture f = new Bootstrap().group(group)
                                             .channel(NioSocketChannel.class)
//                                             .channel(OioSocketChannel.class)
//                                             .handler(new DialogServerInitializer())
//                                             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
//                                             .option(ChannelOption.SO_TIMEOUT, 3000)
                                             .handler(new EchoClientInitializer())
                                             .connect("localhost", 1111)
                                             .sync();

            // Make a new connecti
            // on.
//            ChannelFuture f = b.connect(HOST, PORT).sync();
            System.out.println("connected");
            // Get the handler instance to retrieve the answer.
//            Packet pack = new Packet();
//            Header h = new Header();
//            h.setProto((short) 12);
//            pack.setHeader(h);
//            Thread.sleep(1000);
            Channel ch=f.channel();
            System.out.println("isWritable = "+ch.isWritable());

            ch.writeAndFlush("111111223af");
//            f.channel().flush();
            System.out.println("send");

            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("error",e );
        } finally
        {
            group.shutdownGracefully();
        }
    }
}
