package YaExe;

import java.util.*;

import static java.util.stream.Collectors.*;

public class SymmetricChecker {

    public static void main(String[] args) {
        SymmetricChecker symmetricChecker = new SymmetricChecker();
        List<Point> points = symmetricChecker.initPoints();
        System.out.println(symmetricChecker.check(points));
    }

    private boolean check(List<Point> points) {
        int size = points.size();
        if (size == 1 || size == 0) {
            return true;
        }

        List<List<Double>> xGroupedByY = new ArrayList<>(points.stream()
                .collect(groupingBy(Point::getY, mapping(Point::getX, toList())))
                .values());

        DoubleSummaryStatistics stats = xGroupedByY.get(0)
                .stream()
                .mapToDouble(d -> d)
                .summaryStatistics();

        double median = (stats.getMax() + stats.getMin()) / 2;

        for (List<Double> groupedX : xGroupedByY) {
            int sizeX = groupedX.size();
            while (sizeX != 0) {
                Double left = groupedX.get(0);
                Double right = 2 * median - left;
                if (groupedX.contains(right)) {
                    groupedX.remove(left);
                    groupedX.remove(right);
                } else {
                    return false;
                }
                sizeX = groupedX.size();
            }
        }
        return true;
    }

    private List<Point> initPoints() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(-1, 4));
        points.add(new Point(3, 4));
        points.add(new Point(-4, 4));
        points.add(new Point(6, 4));
        points.add(new Point(0, 2));
        points.add(new Point(2, 2));
        points.add(new Point(1, 1));
        points.add(new Point(1, 50));
        points.add(new Point(1, 2));
        return points;
    }
}
