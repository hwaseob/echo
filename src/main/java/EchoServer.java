import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class EchoServer {
    final static Logger log = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) throws Exception {
        int port = 1111;
        SslContext sslContext= SslContextBuilder.forServer(new File("ssl/server.crt"),
                                                           new File("ssl/server_pk8.pem")).build();
        sslContext= null;
        EventLoopGroup acceptor = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture f = new ServerBootstrap().group(acceptor, worker)
                                                   .channel(NioServerSocketChannel.class)
                                                   .option(ChannelOption.SO_BACKLOG, 100)
//                                                   .handler(new LoggingHandler(LogLevel.INFO))
                                                   .childHandler(new EchoServerInitializer(sslContext))
                                                   .bind(port)
                                                   .sync();
            log.info("listen " + port);
//            log.info("33333334");
            f.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            acceptor.shutdownGracefully();
        }
    }
}
