package lab2.client;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCaseGenerator {
    private static List<String> ops = new ArrayList<>(
            List.of("ECHO", "REVERSE", "UPPER", "LOWER"));

    private static final int MAX_LENGTH = 20;
    private static final int MIN_LENGTH = 10;

    private static final int MAX_COMMAND_COUNT = 10;
    private static final int MIN_COMMAND_COUNT = 5;

    private static final String OUTPUTDIR = "src/main/resources/lab2/testcases";

    public static void generateTestCases() {
        System.out.println("Enter the number of test cases to generate:");
        Scanner scanner = new Scanner(System.in);
        int testCaseCount = scanner.nextInt();

        Random random = new Random();
        Path outputDir = Paths.get(OUTPUTDIR);

        try {
            Files.createDirectories(outputDir);

            for (int i = 0; i < testCaseCount; i++) {
                String filename = "testcase_" + (i + 1) + ".txt";
                Path filePath = outputDir.resolve(filename);

                int commandCount = random.nextInt(MAX_COMMAND_COUNT - MIN_COMMAND_COUNT + 1) + MIN_COMMAND_COUNT;
                List<String> commands = new ArrayList<>();

                for (int j = 0; j < commandCount; j++) {
                    String op = ops.get(random.nextInt(ops.size()));
                    String content = generateContent(random);
                    commands.add(op + ":" + content);
                }
                commands.add("EXIT:bye");
                Files.write(filePath, commands);
                System.out.println("Generated test case: " + filePath.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateContent(Random random) {
        int length = random.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char ch;
            if (random.nextBoolean()) {
                ch = (char) ('a' + random.nextInt(26));
            } else {
                ch = (char) ('A' + random.nextInt(26));
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        generateTestCases();
    }
}
