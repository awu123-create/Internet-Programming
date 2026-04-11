package homework1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class TextWriter {
    // 功能：从键盘读取多行英文，写入 '学号1.txt'，并统计总字符数追加到文件末尾
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/homework1/学号1.txt"))) {
            System.out.println("请输入内容：");
            String line;
            long charCount = 0;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                charCount += line.length();
            }
            writer.write("this document contains " + charCount + " bytes in total.");
            System.out.println("内容写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
