package Genoms;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        FileWriter writer = new FileWriter("/home/danil/insertGenoms.sql", false);
        writer.write("delete from test.genoms;\n");
        writer.flush();
        writePairsInsertToFile("/home/danil/IdeaProjects/SUHD/src/Genoms/Genome_1.txt", 1, 2);
        writePairsInsertToFile("/home/danil/IdeaProjects/SUHD/src/Genoms/Genome_2.txt", 2, 2);
        writePairsInsertToFile("/home/danil/IdeaProjects/SUHD/src/Genoms/Genome_1.txt", 1, 5);
        writePairsInsertToFile("/home/danil/IdeaProjects/SUHD/src/Genoms/Genome_2.txt", 2, 5);
        writePairsInsertToFile("/home/danil/IdeaProjects/SUHD/src/Genoms/Genome_1.txt", 1, 9);
        writePairsInsertToFile("/home/danil/IdeaProjects/SUHD/src/Genoms/Genome_2.txt", 2, 9);
    }

    private static void writePairsInsertToFile(String filePath, Integer fileId, Integer shLenght) throws IOException {
        List<String> g1 = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        HashSet<String> setG1 = new HashSet<>();
        String prev = null;
        for (String s : g1) {
            if (prev != null) {
                setG1.add(prev + s.charAt(0));
            }
            for (int i = 0; i <= s.length() - shLenght; i++) {
                setG1.add(s.substring(i, i + shLenght));
            }
            prev = s.substring(s.length() - shLenght + 1);
        }

        try (FileWriter writer = new FileWriter("/home/danil/insertGenoms.sql", true)) {
            setG1.forEach(s -> {
                try {
                    writer.write("INSERT INTO test.genoms VALUES(" + fileId + ", '" + s + "');\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
        }
    }
}
