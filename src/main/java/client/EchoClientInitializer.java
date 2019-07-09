package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

public class EchoClientInitializer extends ChannelInitializer<SocketChannel> {
    SslContext sslContext;

    public EchoClientInitializer() {
        //super();
    }

    public EchoClientInitializer(SslContext sslContext) {
        //super();
        this.sslContext=sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslContext != null)
        {
            p.addLast(sslContext.newHandler(ch.alloc()));
        }
        p.addLast(new LoggingHandler(LogLevel.INFO));
        p.addLast(new StringEncoder());
        p.addLast(new StringDecoder());
        p.addLast(new EchoClientHandler());
    }
}
