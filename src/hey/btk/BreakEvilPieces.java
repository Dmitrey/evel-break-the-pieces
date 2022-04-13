package hey.btk;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BreakEvilPieces {

    public static void main(String[] args) {
        String shape = String.join("\n", new String[]{
                "+---------------+",
                "|  +----+       |",
                "|  |    |  +-++ |",
                "|  |    +--+ || |",
                "|  |    |  +-++ |",
                "|  +----+       |",
                "+---------------+"});
        solve(shape).forEach(System.out::println);
    }

    public static List<String> solve(String shape) {
        System.out.println(shape);
        String[][] matrix = DirectionHolder.stringToArray(shape);
        int[][] starts = findStarts(matrix);
        List<Figure> res = new ArrayList<>();

        mark:
        for (int[] _start : starts) {
            DirectionHolder holder = new DirectionHolder(matrix, _start);
            while (true) {
                holder.stepForward();
                if (Arrays.equals(holder.getPoint(), _start)) {
                    break;
                }
                if (holder.canGoRight()) {
                    holder.turnRight();
                    holder.rightCount++;
                } else {
                    if (!holder.canGoForward()) {
                        if (!holder.canGoLeft()) {

                            continue mark;
                        }
                        holder.turnLeft();
                        holder.leftCount++;
                    }
                }

//                else {
//                    int i = 0;
//                    while (!holder.canGoForward()) {
//                        i++;
//                        holder.turnRight();
//                        if (holder.isPrevious()) {
//                            holder.turnRight();
//                            i++;
//                        }
//                        if (i == 3) {
//                            holder.leftCount++;
//                        }
//                    }
//                }
            }
            if (holder.rightCount > holder.leftCount) {
                res.add(new Figure(holder.getFigure(), holder.getPointsList()));
            }
        }

        res.forEach(f -> f.getPoints().sort((a, b) -> { //сортируем массивы с координатами точек
            if (a[0] > b[0]) {
                return 1;
            }
            if (b[0] > a[0]) {
                return -1;
            }
            return Integer.compare(a[1], b[1]);
        }));

        HashMap<Integer, Figure> map = new HashMap<>(); //откидываем фигуры с одинаковыми массивами точек, т.е. дубликаты
        for (Figure figure : res) {
            map.put(figure.hashCode(), figure);
        }
        res.clear();
        res.addAll(map.values());
        findInsertions2(res);
        createInsertions(res);

        List<String> finalList = res.stream()
                .map(Figure::getFigure)
                .map(DirectionHolder::stringToArray)
                .map(DirectionHolder::deleteStartSpaces)
                .map(s -> s.replaceAll(" ^", ""))
                .collect(Collectors.toList());

        return finalList;
    }

    private static void createInsertions(List<Figure> res) {

        for (Figure outer : res) {
            if (outer.getInnerFigures().size() > 0) {
                insertInner(outer);
            }
        }
    }

    private static void findInsertions2(List<Figure> list) {
        for (Figure fig1 : list) {
            for (Figure fig2 : list) {
                if (fig1 != fig2) {
                    for (int[] point : fig1.getPoints()) {
                        boolean con = contains(fig2.getPoints(), point);
                        boolean inside = isInside(fig2.getPoints(), fig1.getPoints().get(0));
//                        boolean thin = isThin(fig1);
                        if (!con && inside) { //если нет пересечений и стартовая точка внутри
                            fig2.addInnerFigure(fig1);
//                            insert(fig1, fig2); //во второй вставляется первый
//                            makeInsertions2(list);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void insertInner(Figure fig) {
        String layer = fig.getFigure().replaceAll("\\+|-|\\|"," ");
        Figure layerFigure = new Figure(layer,fig.getPoints());
        for (Figure f:fig.getInnerFigures()) {
            insert(f,layerFigure);
        }
//        System.out.println(layerFigure.getFigure());

        String[][] matrix = DirectionHolder.stringToArray(layerFigure.getFigure());
//        int[][] starts = findStarts(matrix);

        int[] _start = findStarts(matrix)[0];
            DirectionHolder holder = new DirectionHolder(matrix, _start);
            while (true) {
                holder.stepForward();
                if (Arrays.equals(holder.getPoint(), _start)) {
                    break;
                }
                if (holder.canGoLeft()) {
                    holder.turnLeft();
                    holder.leftCount++;
                } else {
                    if (!holder.canGoForward()) {
                        holder.turnRight();
                        holder.rightCount++;
                    }
                }
            }
            if (holder.rightCount > holder.leftCount) {

                Figure figure = new Figure(holder.getFigure(), holder.getPointsList());
                figure.getPoints().sort((a, b) -> { //сортируем массивы с координатами точек
                    if (a[0] > b[0]) {
                        return 1;
                    }
                    if (b[0] > a[0]) {
                        return -1;
                    }
                    return Integer.compare(a[1], b[1]);
                });
                insert(figure,fig);
            }
    }

    //во второй вставляется первый
    private static void insert(Figure inner, Figure outer) { // вызывается чаще чем нужно
        int maxX = outer.getPoints().stream().mapToInt(x -> x[1]).max().getAsInt();
        int maxY = outer.getPoints().stream().mapToInt(x -> x[0]).max().getAsInt();
        String[][] matrix = new String[maxY + 1][maxX + 1];
        for (String[] str : matrix) {
            Arrays.fill(str, " ");
        }
        String[][] fig2Arr = DirectionHolder.stringToArray(outer.getFigure());
        int offsetX = outer.getPoints().get(0)[1];
        int offsetY = outer.getPoints().get(0)[0];

        for (int[] a : outer.getPoints()) {
            String s = fig2Arr[a[0] - offsetY][a[1] - offsetX];
            matrix[a[0]][a[1]] = s;
        }

        String[][] fig1Arr = DirectionHolder.stringToArray(inner.getFigure());
        offsetX = inner.getPoints().get(0)[1];
        offsetY = inner.getPoints().get(0)[0];

        for (int[] a : inner.getPoints()) {
            String s = fig1Arr[a[0] - offsetY][a[1] - offsetX];
            matrix[a[0]][a[1]] = s;
        }

        outer.setFigure(DirectionHolder.arrayToStringWithoutTrimming(matrix));
        outer.getPoints().addAll(inner.getPoints());
    }

    private static boolean isInside(List<int[]> points, int[] point) {
        int minX, maxX, minY, maxY;
        minX = points.stream().mapToInt(x -> x[1]).min().getAsInt();
        maxX = points.stream().mapToInt(x -> x[1]).max().getAsInt();
        minY = points.stream().mapToInt(x -> x[0]).min().getAsInt();
        maxY = points.stream().mapToInt(x -> x[0]).max().getAsInt();
        return (point[1] > minX && point[1] < maxX && point[0] > minY && point[0] < maxY);
    }

    public static boolean contains(List<int[]> list, int[] point) {
        for (int[] arr : list) {
            if (Arrays.equals(arr, point))
                return true;
        }
        return false;
    }

    private static int[][] findStarts(String[][] matrix) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals("+") && new DirectionHolder(matrix, new int[]{i, j}).canGoForward()
                        && new DirectionHolder(matrix, new int[]{i, j}).canGoRight()) {
                    list.add(new int[]{i, j});
                }
            }
        }
        return list.toArray(new int[0][]);
    }
}

class Figure {
    private String figure;
    private List<int[]> points;
    private Map<Integer, Figure> figures;

    public Figure(String figure, List<int[]> points) {
        this.figure = figure;
        this.points = points;
        figures = new HashMap();
    }

    public List<int[]> getPoints() {
        return points;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public void addInnerFigure(Figure figure) {
        figures.put(figure.hashCode(), figure);
    }

    public List<Figure> getInnerFigures() {
        return new ArrayList<>(figures.values());
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int[] arr : points) {
            result += arr[0] * 100 + arr[1] * 10000;
        }
        return result;
    }
}

class DirectionHolder {
    private int index = 0;
    private int[] point;
    private int[] prevPoint;
    private final String[][] matrix;
    private final String[][] resMatrix;
    public int leftCount;
    public int rightCount;
    private final List<int[]> pointsList = new ArrayList<>();
    private final List<Function<int[], int[]>> list = List.of(
            (int[] arr) -> new int[]{arr[0], arr[1] + 1},
            (int[] arr) -> new int[]{arr[0] + 1, arr[1]},
            (int[] arr) -> new int[]{arr[0], arr[1] - 1},
            (int[] arr) -> new int[]{arr[0] - 1, arr[1]}
    );
    private Function<int[], int[]> currentDirection;
    private Function<int[], int[]> rightDirection;
    private Function<int[], int[]> leftDirection;

    public DirectionHolder(String[][] matrix, int[] start) {
        this.matrix = matrix;
        int max = Arrays.stream(matrix).mapToInt(arr -> arr.length).max().getAsInt();
        this.resMatrix = new String[matrix.length][max];
        for (String[] strings : resMatrix) {
            Arrays.fill(strings, " ");
        }
        point = start;
        updateDirections();
    }

    void updateDirections() {
        currentDirection = list.get(index);
        try {
            rightDirection = list.get(index + 1);
        } catch (Exception e) {
            rightDirection = list.get(0);
        }
        try {
            leftDirection = list.get(index - 1);
        } catch (Exception e) {
            leftDirection = list.get(3);
        }
    }

    public void stepForward() {
        prevPoint = point;
        point = currentDirection.apply(point);
        pointsList.add(point);
        show();
    }

    public boolean canGoRight() {
        int[] checkPoint = rightDirection.apply(point);
//        if (pointsList.contains(checkPoint)){
//            return false;
//        }
        for (int[] a : pointsList) {
            if (Arrays.equals(a, checkPoint)) {
                return false;
            }
        }
        String element;
        try {
            element = matrix[checkPoint[0]][checkPoint[1]];
            if (prevPoint != null) {
                if (matrix[point[0]][point[1]].equals("+") && matrix[prevPoint[0]][prevPoint[1]].equals("-"))
                    return element.equals("|") || element.equals("+");
                if (matrix[point[0]][point[1]].equals("+") && matrix[prevPoint[0]][prevPoint[1]].equals("|"))
                    return element.equals("-") || element.equals("+");
            }
            if (matrix[point[0]][point[1]].equals("+"))
                return element.equals("-") || element.equals("|") || element.equals("+");
//            if (matrix[point[0]][point[1]].equals("-"))
//                return element.equals("|") || element.equals("+");
//            if (matrix[point[0]][point[1]].equals("|"))
//                return element.equals("-") || element.equals("+");
            return false;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean canGoLeft() {
        int[] checkPoint = leftDirection.apply(point);
        for (int[] a : pointsList) {
            if (Arrays.equals(a, checkPoint)) {
                return false;
            }
        }
        String element;
        try {
            element = matrix[checkPoint[0]][checkPoint[1]];
            if (prevPoint != null) {
                if (matrix[point[0]][point[1]].equals("+") && matrix[prevPoint[0]][prevPoint[1]].equals("-"))
                    return element.equals("|") || element.equals("+");
                if (matrix[point[0]][point[1]].equals("+") && matrix[prevPoint[0]][prevPoint[1]].equals("|"))
                    return element.equals("-") || element.equals("+");
            }
            if (matrix[point[0]][point[1]].equals("+"))
                return element.equals("-") || element.equals("|") || element.equals("+");
//            if (matrix[point[0]][point[1]].equals("-"))
//                return element.equals("|") || element.equals("+");
//            if (matrix[point[0]][point[1]].equals("|"))
//                return element.equals("-") || element.equals("+");
            return false;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean canGoForward() {
        int[] checkPoint = currentDirection.apply(point);
        String element;
        try {
            element = matrix[checkPoint[0]][checkPoint[1]];
            if (prevPoint != null) {
                if (matrix[point[0]][point[1]].equals("+") && matrix[prevPoint[0]][prevPoint[1]].equals("-"))
                    return element.equals("-") || element.equals("+");
                if (matrix[point[0]][point[1]].equals("+") && matrix[prevPoint[0]][prevPoint[1]].equals("|"))
                    return element.equals("|") || element.equals("+");
            }
            return element.equals("-") || element.equals("|") || element.equals("+");
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public void turnRight() {
        index = index < 3 ? index + 1 : 0;
        updateDirections();
    }

    public void turnLeft() {
        index = index > 0 ? index - 1 : 3;
        updateDirections();
    }

    public void show() {
        resMatrix[point[0]][point[1]] = matrix[point[0]][point[1]];
    }

    public int[] getPoint() {
        return point;
    }

    public List<int[]> getPointsList() {
        return pointsList;
    }

    public boolean isPrevious() {
        int[] checkPoint = currentDirection.apply(point);
        return Arrays.equals(checkPoint, prevPoint);
    }

    public static String deleteStartSpaces(String[][] matrix) {
        StringBuilder res = new StringBuilder();
        List<Integer> spacesList = new ArrayList<>();

        for (String[] strings : matrix) {
            int spaces = 0;
            for (String string : strings) {
                if (string.equals(" ")) {
                    spaces++;
                } else {
                    spacesList.add(spaces);
                    break;
                }
            }
        }

        int min = spacesList.stream().mapToInt(x -> x).min().getAsInt();
        String mask = "^ {" + min + "}";
        for (String[] strings : matrix) {
            StringBuilder chars = new StringBuilder();
            for (String string : strings) {
                chars.append(string);
            }
            String str = chars.toString().replaceAll(" *$", "");
            str = str.replaceAll(mask, "");
            res.append(str);
            if (!str.matches(" *")) {
                res.append("\n");
            }
        }

        return res.toString().replaceAll("\n$", "");
    }

    public String getFigure() {
        String resultFigure = deleteStartSpaces(resMatrix);
        Pattern pattern;
        Matcher matcher;
        while (true) {
            pattern = Pattern.compile("-+\\++-+");
            matcher = pattern.matcher(resultFigure);
            if (matcher.find()) {
                int length = matcher.group().length();
                StringBuilder replacement = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    replacement.append("-");
                }
                resultFigure = resultFigure.replace(matcher.group(), replacement);
            } else break;
        }
        while (true) {
            pattern = Pattern.compile("\\+\\+-+");
            matcher = pattern.matcher(resultFigure);
            if (matcher.find()) {
                int length = matcher.group().length();
                StringBuilder replacement = new StringBuilder("+");
                for (int i = 0; i < length - 1; i++) {
                    replacement.append("-");
                }
                resultFigure = resultFigure.replace(matcher.group(), replacement);
            } else break;
        }
        while (true) {
            pattern = Pattern.compile("-+\\+\\+");
            matcher = pattern.matcher(resultFigure);
            if (matcher.find()) {
                int length = matcher.group().length();
                StringBuilder replacement = new StringBuilder();
                for (int i = 0; i < length - 1; i++) {
                    replacement.append("-");
                }
                replacement.append("+");
                resultFigure = resultFigure.replace(matcher.group(), replacement);
            } else break;
        }

        String[][] arr1 = stringToArray(resultFigure);
        String[][] transpone = transpone(arr1);
        String transpStr = arrayToString(transpone);
// for |
        while (true) {
            pattern = Pattern.compile("\\|+\\++\\|+");
            matcher = pattern.matcher(resultFigure);
            if (matcher.find()) {
                int length = matcher.group().length();
                StringBuilder replacement = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    replacement.append("|");
                }
                resultFigure = resultFigure.replace(matcher.group(), replacement);
            } else break;
        }
        while (true) {
            pattern = Pattern.compile("\\+\\+\\|+");
            matcher = pattern.matcher(resultFigure);
            if (matcher.find()) {
                int length = matcher.group().length();
                StringBuilder replacement = new StringBuilder("+");
                for (int i = 0; i < length - 1; i++) {
                    replacement.append("|");
                }
                resultFigure = resultFigure.replace(matcher.group(), replacement);
            } else break;
        }
        while (true) {
            pattern = Pattern.compile("\\|+\\+\\+");
            matcher = pattern.matcher(resultFigure);
            if (matcher.find()) {
                int length = matcher.group().length();
                StringBuilder replacement = new StringBuilder();
                for (int i = 0; i < length - 1; i++) {
                    replacement.append("|");
                }
                replacement.append("+");
                resultFigure = resultFigure.replace(matcher.group(), replacement);
            } else break;
        }

        pattern = Pattern.compile("\\+\\+\\+");
        matcher = pattern.matcher(transpStr);
        if (matcher.find()) {
            transpStr = transpStr.replace(matcher.group(), "+|+");
        }

        transpone = stringToArray(transpStr);
        arr1 = transpone(transpone);
        resultFigure = arrayToString(arr1);
        return resultFigure;
    }

    public String getFigureWithoutTrimming(){
        String resultFigure = arrayToStringWithoutTrimming(resMatrix);
        return resultFigure;
    }

    public static String[][] stringToArray(String str) {
        String[] arr = str.split("\n");
        String[][] matrix = new String[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            matrix[i] = arr[i].split("");
        }
        return matrix;
    }

    public static String arrayToString(String[][] arr) {
        StringBuilder res = new StringBuilder();
        for (String[] strings : arr) {
            StringBuilder chars = new StringBuilder();
            for (String string : strings) {
                chars.append(string);
            }
            String str = chars.toString().replaceAll(" *$", "");
            if (!str.matches(" *"))
                res.append(str).append("\n");

        }
        String result = res.toString();
        result = result.replaceAll("\n$", "");
        return result;
    }

    public static String arrayToStringWithoutTrimming(String[][] arr) {
        StringBuilder res = new StringBuilder();
        for (String[] strings : arr) {
            StringBuilder chars = new StringBuilder();
            for (String string : strings) {
                chars.append(string);
            }
            String str = chars.toString();

                res.append(str).append("\n");

        }
        String result = res.toString();
        return result;
    }

    public static String[][] transpone(String[][] matrix) {
        Optional<String[]> maxOp = Arrays.stream(matrix).max((arr1, arr2) -> Integer.compare(arr1.length, arr2.length));
        int max = maxOp.get().length;
        String[][] transp = new String[max][matrix.length];
        for (String[] s : transp) {
            Arrays.fill(s, " ");
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transp[j][i] = matrix[i][j];
            }
        }
        return transp;
    }
}