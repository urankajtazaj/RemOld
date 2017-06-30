import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by uran on 30/06/17.
 */
public class ReadFileAttr {

    Scanner sc = new Scanner(System.in);

    public ReadFileAttr(String directory, int days) {
        try {
            List<String> fileList = getFiles(directory, days);

            System.out.print("Do you want to delete " + fileList.size() + " files? [y/n]: ");
            String resp = sc.next();
            System.out.println();

            while (!resp.matches("y|Y|n|N")) {
                System.out.print("Do you want to delete " + fileList.size() + " files? [y/n]: ");
                resp = sc.next();
                System.out.println();
            }

            if (resp.equals("y") || resp.equals("Y")) {
                for (String s : fileList)
                    new File(s).delete();
                System.out.println(fileList.size() + " deleted successfully!");
            } else {
                System.out.println("Operation canceled");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private List<String> getFiles(String directory, int days) throws IOException {
        List<String> fileList = new ArrayList<>();
        File dir = new File(directory);

        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                Path p = Paths.get(f.getPath());
                BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

                Date ft = new Date(attr.lastAccessTime().toMillis());
                long dateDiff = ((new Date().getTime() - ft.getTime()) / (1000 * 60 * 60 * 24));

                if (dateDiff >= days) {
                    fileList.add(f.getAbsolutePath());
                }
            }
        }
        return fileList;
    }
}
