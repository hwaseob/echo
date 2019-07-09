import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {
    SslContext sslContext;

    public EchoServerInitializer() {
        //super();
    }

    public EchoServerInitializer(SslContext sslContext) {
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
//        p.addLast(new MessageToMessageDecoder<ByteBuf>())
        p.addLast(new EchoServerHandler());
    }
}
