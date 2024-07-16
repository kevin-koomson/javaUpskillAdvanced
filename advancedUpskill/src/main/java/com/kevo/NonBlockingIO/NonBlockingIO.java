package com.kevo.NonBlockingIO;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class NonBlockingIO {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/java/com/kevo/NonBlockingIO/data.txt");
        try(FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead;
            do {
                bytesRead = channel.read(buffer);
                if (bytesRead > 0) {
                    System.out.println(new String(buffer.array(), 0, bytesRead));
                    buffer.clear();
                }
            } while (bytesRead > 0);
        }
    }
}
