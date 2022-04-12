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
        System.out.println("actual:");
        actual.forEach(System.out::println);
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        String shape = String.join("\n", new String[]{
                "+-+  ",
                "| +-+",
                "+-+ |",
                "| +-+",
                "+-+ |",
                "| +-+",
                "+-+ |",
                "| +-+",
                "+-+ |",
                "  +-+"
        });

        List<String> expected = new ArrayList<>(List.of(
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                }),
                String.join("\n", new String[]{"" +
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                })
        ));
        List<String> actual = BreakEvilPieces.solve(shape);
        System.out.println("actual:");
        actual.forEach(System.out::println);
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        String shape = String.join("\n", new String[]{
                "++",
                "||",
                "||",
                "||",
                "|+---------------+",
                "|             +--+",
                "|             |",
                "|             +--+",
                "+----------------+"
        });

        List<String> expected = new ArrayList<>(List.of(
                String.join("\n", new String[]{
                        "++",
                        "||",
                        "||",
                        "||",
                        "|+---------------+",
                        "|             +--+",
                        "|             |",
                        "|             +--+",
                        "+----------------+"
                })
        ));
        List<String> actual = BreakEvilPieces.solve(shape);
        System.out.println("actual:");
        actual.forEach(System.out::println);
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        String shape = String.join("\n", new String[]{
                "  ++ ++ ++                ",
                "  || || ||                ",
                "  || || ||                ",
                " +++-++-++--------------+ ",
                " |                      | ",
                " ++-++-++---------------+ ",
                " || || ||                 ",
                " || || ||                 ",
                " ++ ++ ++  "
        });

        List<String> expected = new ArrayList<>(List.of(
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }),
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }),
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }),
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }),
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }),
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }),
                String.join("\n", new String[]{
                        "+----------------------+\n" +
                                "|                      |\n" +
                                "+----------------------+"
                })
        ));
        List<String> actual = BreakEvilPieces.solve(shape);
        System.out.println("actual:");
        actual.forEach(System.out::println);
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test5() {
        String shape = String.join("\n", new String[]{
                "  ++ ++ ++ ++ ++ ++",
                "  || || || || || ||",
                "  || || || || || ||",
                " ++++++++++++++++++",
                " || || || || || || ",
                " || || || || || || ",
                " ++ ++ ++ ++ ++ ++"
        });

        List<String> expected = new ArrayList<>(List.of(
                String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                }), String.join("\n", new String[]{
                        "++\n" +
                                "||\n" +
                                "||\n" +
                                "++"
                })
        ));
        List<String> actual = BreakEvilPieces.solve(shape);
        System.out.println("actual:");
        actual.forEach(System.out::println);
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        Assert.assertEquals(expected, actual);
    }
}