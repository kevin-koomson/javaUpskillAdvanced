package com.kevo.NonBlockingIO;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class WriteToFile {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/java/com/kevo/NonBlockingIO/written.txt");
        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            String data = "Hello World!";
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
}
