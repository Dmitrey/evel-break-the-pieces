package hey.btk;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class BreakPiecesTest {

    @Test
    public void test1() {
        String shape = String.join("\n", new String[]{
                "                                         ",
                "            +------------+--+      +--+  ",
                "            |            |  |      |  |  ",
                "            | +-------+  |  |      |  |  ",
                "            | |       |  |  +------+  |  ",
                "            | |       |  |            |  ",
                "            | |       |  |    +-------+  ",
                "            | +-------+  |    |          ",
                "    +-------+            |    |          ",
                "    |       |            |    +-------+  ",
                "    |       |            |            |  ",
                "    +-------+            |            |  ",
                "            |            |            |  ",
                "       +----+---+--+-----+------------+  ",
                "       |    |   |  |     |            |  ",
                "       |    |   |  +-----+------------+  ",
                "       |    |   |                     |  ",
                "       +----+---+---------------------+  ",
                "       |    |                         |  ",
                "       |    | +----+                  |  ",
                "   +---+    | |    |     +------------+  ",
                "   |        | |    |     |               ",
                "   +--------+-+    +-----+               ",
                "                                         "
        });

        List<String> expected = new ArrayList<>(List.of(
                String.join("\n", new String[]{"" +
                        "+--+",
                        "|  |",
                        "|  +------------------+",
                        "|                     |",
                        "+---------------------+"}),
                String.join("\n", new String[]{"" +
                        "+----+",
                        "|    |",
                        "|    |",
                        "|    |",
                        "+----+"}),
                String.join("\n", new String[]{"" +
                        "+--+      +--+",
                        "|  |      |  |",
                        "|  |      |  |",
                        "|  +------+  |",
                        "|            |",
                        "|    +-------+",
                        "|    |",
                        "|    |",
                        "|    +-------+",
                        "|            |",
                        "|            |",
                        "|            |",
                        "+------------+"}),
                String.join("\n", new String[]{"" +
                        "+-------+",
                        "|       |",
                        "|       |",
                        "|       |",
                        "+-------+"}),
                String.join("\n", new String[]{"" +
                        "+------------+",
                        "|            |",
                        "| +-------+  |",
                        "| |       |  |",
                        "| |       |  |",
                        "| |       |  |",
                        "| +-------+  |",
                        "+            |",
                        "|            |",
                        "|            |",
                        "+            |",
                        "|            |",
                        "+------------+"}),
                String.join("\n", new String[]{"" +
                        "+-------------------------+",
                        "|                         |",
                        "| +----+                  |",
                        "| |    |     +------------+",
                        "| |    |     |",
                        "+-+    +-----+"}),
                String.join("\n", new String[]{"" +
                        "+---+",
                        "|   |",
                        "|   |",
                        "|   |",
                        "+---+"}),
                String.join("\n", new String[]{"" +
                        "+------------+",
                        "|            |",
                        "+------------+"}),
                String.join("\n", new String[]{"" +
                        "+-------+",
                        "|       |",
                        "|       |",
                        "+-------+"}),
                String.join("\n", new String[]{"" +
                        "    +----+",
                        "    |    |",
                        "    |    |",
                        "+---+    |",
                        "|        |",
                        "+--------+"}),
                String.join("\n", new String[]{"" +
                        "+-----+",
                        "|     |",
                        "+-----+"})
        ));

        List<String> actual = BreakEvilPieces.solve(shape);
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        Assert.assertEquals(expected, actual);
    }

}