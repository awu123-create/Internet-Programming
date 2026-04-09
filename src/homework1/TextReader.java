package homework1;

import java.io.BufferedReader;
import java.io.FileReader;

public class TextReader {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/homework1/学号1.txt"))) {
            String line;
            System.out.println("文件内容：");
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(" ", "");
                System.out.println(line);
            }
            System.out.println("文件读取完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}