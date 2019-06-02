import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EchoServer {
    final static Logger log = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) throws Exception {
        int port = 1111;
        EventLoopGroup acceptor = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture f = new ServerBootstrap().group(acceptor, worker)
                                                   .channel(NioServerSocketChannel.class)
                                                   .option(ChannelOption.SO_BACKLOG, 100)
//                                                   .handler(new LoggingHandler(LogLevel.INFO))
                                                   .childHandler(new ChannelInitializer<SocketChannel>() {
                                                       @Override
                                                       protected void initChannel(SocketChannel socketChannel) {
                                                           ChannelPipeline p = socketChannel.pipeline();
                                                           p.addLast(new LoggingHandler(LogLevel.INFO));
                                                           p.addLast(new EchoServerHandler());
                                                       }
                                                   })
                                                   .bind(port)
                                                   .sync();
            log.info("listen " + port);
            log.info("2222222223334");
            f.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            acceptor.shutdownGracefully();
        }
    }
}
