import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by uran on 30/06/17.
 */
public class cleanFiles {

    private static Scanner sc = new Scanner(System.in);
    private static final int DAYS = 60;

    public static void main(String[] args) {
        if (args.length == 1) {
            if (args[0].equals("-h") || args[0].equals("help")) printUsage();
            else ReadFileAttr(args[0], DAYS);
        }else if (args.length == 2) {
            ReadFileAttr(args[0], Integer.parseInt(args[1]));
        }else {
            ReadFileAttr(System.getProperty("user.home"), DAYS);
        }
    }

    private static void printUsage(){
        System.out.println("\n\nUSAGE\n");
        System.out.println("cleanFiles [dir/to/clean] [not used since]");
        System.out.println("\nExamples:");
        System.out.println("java clenaFiles [-h | help]           \t - Shows this menu");
        System.out.println("java cleanFiles /user/home/Downloads 30\t - Deletes all files that are in the 'Downloads' directory and not been used for 30 days");
        System.out.println("java cleanFiles /user/home/Desktop   \t - Deletes all files that are in the 'Desktop' directory and not been used for 60[default] days");
        System.out.println("java cleanFiles                      \t - Deletes all files that are in the 'home'[default] directory and not been used for 60[default] days");
    }

    private static void ReadFileAttr (String directory, int days){
        double size = 0;
        DecimalFormat df = new DecimalFormat("###,###.##MB");
        try {
            List<String> fileList = getFiles(directory, days);

            if (fileList.size() > 0) {
                System.out.println("\nFiles to be deleted:");
                for (String s : fileList) {
                    System.out.println(s);
                }
                System.out.print("\nDo you want to delete " + fileList.size() + " files? [y/n]: ");
                String resp = sc.next();

                while (!resp.matches("y|Y|n|N")) {
                    System.out.print("Do you want to delete " + fileList.size() + " files? [y/n]: ");
                    resp = sc.next();
                }

                if (resp.equals("y") || resp.equals("Y")) {
                    for (String s : fileList) {
                        File f = new File(s);
                        size += f.length();
                        f.delete();
                    }
                    System.out.println(fileList.size() + " files deleted successfully!");
                    System.out.println(df.format(size / 1024) + " freed!");
                } else {
                    System.out.println("Operation canceled");
                }
            }else {
                System.out.println("No files that match your criteria have been found.");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            System.err.println("Directory is not valid");
        }
    }

    private static List<String> getFiles(String directory, int days) throws IOException {
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
