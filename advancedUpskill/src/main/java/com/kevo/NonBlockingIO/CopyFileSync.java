package com.kevo.NonBlockingIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class CopyFileSync {
    public static void main(String[] args) throws IOException {
        Path source = Path.of("src/main/java/com/kevo/NonBlockingIO/data.txt");
        Path dest = Path.of("src/main/java/com/kevo/NonBlockingIO/dest.txt");
        try(FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);
            FileChannel destChannel = FileChannel.open(dest, StandardOpenOption.WRITE, StandardOpenOption.CREATE)){

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int bytesRead;

            do {
                bytesRead = sourceChannel.read(buffer);
                if(bytesRead > 0) {
                    buffer.flip();
                    String data = new String(buffer.array(), 0, bytesRead);
                    System.out.println(data);
                    ByteBuffer destBuffer = ByteBuffer.wrap(data.getBytes());
                    destChannel.write(destBuffer);
                    buffer.clear();
                    System.out.println("Writing...");
                }
            } while (bytesRead > 0);
            System.out.println("Copy completed");
        } catch (IOException exception){
            System.out.println("An Error Occurred");
            exception.printStackTrace();
        }
    }
}
