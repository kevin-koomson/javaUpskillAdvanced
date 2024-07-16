package com.kevo.NonBlockingIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

public class CopyFileAsync {
    public static void main(String[] args) throws IOException {
        Path source = Path.of("src/main/java/com/kevo/NonBlockingIO/data.txt");
        Path dest = Path.of("src/main/java/com/kevo/NonBlockingIO/dest.txt");

        try(
                AsynchronousFileChannel sourceChannel = AsynchronousFileChannel.open(source, StandardOpenOption.READ);
                AsynchronousFileChannel destChannel = AsynchronousFileChannel.open(dest, StandardOpenOption.WRITE, StandardOpenOption.CREATE)){

            ByteBuffer buffer = ByteBuffer.allocate(1024);
        }
    }
    class CopyHandler implements CompletionHandler<Integer, Void> {
        private final AsynchronousFileChannel sourceChannel;
        private final AsynchronousFileChannel destChannel;
        private final ByteBuffer buffer;
        private long position = 0;
        private final CompletableFuture<Void> completionFuture = new CompletableFuture<>();

        CopyHandler(AsynchronousFileChannel sourceChannel, AsynchronousFileChannel destChannel, ByteBuffer buffer) {
            this.sourceChannel = sourceChannel;
            this.destChannel = destChannel;
            this.buffer = buffer;
        }
        public CompletableFuture<Void> getCompletionFuture(){
            return completionFuture;
        }


        @Override
        public void completed(Integer result, Void attachment) {
            if(result == -1){
                completionFuture.complete(null);
                return;
            }

            buffer.flip();
            destChannel.write(buffer, position, null, new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    position += result;
                    buffer.clear();
                    sourceChannel.read(buffer, position, null, CopyHandler.this);
                }
                @Override
                public void failed(Throwable exc, Void attachment) {
                    exc.printStackTrace();
                    completionFuture.completeExceptionally(exc);
                }
            });
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            exc.printStackTrace();
            completionFuture.completeExceptionally(exc);
        }
    }
}
