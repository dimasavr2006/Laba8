import org.example.utils.DocumentFieldBuilder;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        DocumentFieldBuilder dfb = new DocumentFieldBuilder();
        Scanner sc = new Scanner(System.in);
        System.out.println(dfb.build(sc, null));

    }
}
