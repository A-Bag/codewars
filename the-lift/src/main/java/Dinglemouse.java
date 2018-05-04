import java.util.*;
import java.util.stream.Collectors;

public class Dinglemouse {

    public static int[] theLift(final int[][] queues, final int capacity) {
        List<Integer> result = new ArrayList<>();
        List<List<Integer>> dynamicQueues = new ArrayList<>();
        Arrays.stream(queues).forEach(queue -> {
            List<Integer> queueAsList = Arrays.stream(queue).boxed().collect(Collectors.toList());
            dynamicQueues.add(new ArrayList<>(queueAsList));
        });
        List<Integer> peopleInTheLift = new ArrayList<>();

        while(arePeopleWaitingForTheLift(dynamicQueues) || peopleInTheLift.size()>0) {
            for (int floor = 0; floor < dynamicQueues.size(); floor++) {
                if (arePeopleAtThisFloorWantingToGoUp(floor, dynamicQueues)
                        || isSomeoneGettingOffAtThisFloor(floor, peopleInTheLift)) {
                    stopAtThisFloor(floor, result);
                    getPeopleOutOfTheLift(floor, peopleInTheLift);
                    takePeopleThatWantToGoUp(floor, dynamicQueues.get(floor), peopleInTheLift, capacity);
                }
            }
            for (int floor = dynamicQueues.size() - 1; floor > -1; floor--) {
                if (arePeopleAtThisFloorWantingToGoDown(floor, dynamicQueues)
                        || isSomeoneGettingOffAtThisFloor(floor, peopleInTheLift)) {
                    stopAtThisFloor(floor, result);
                    getPeopleOutOfTheLift(floor, peopleInTheLift);
                    takePeopleThatWantToGoDown(floor, dynamicQueues.get(floor), peopleInTheLift, capacity);
                }
            }
        }
        if (result.size() == 0) {
            return new int[]{0};
        }
        if (result.get(0) != 0) {
            result.add(0, 0);
        }
        if (result.get(result.size()-1) != 0) {
            result.add(0);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    private static boolean arePeopleWaitingForTheLift(List<List<Integer>> dynamicQueues){
        boolean result = false;
        for (List<Integer> queue: dynamicQueues) {
            if (queue.size() > 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    private static boolean arePeopleAtThisFloorWantingToGoUp(int floor, List<List<Integer>> dynamicQueues) {
        boolean result = false;
        if (dynamicQueues.get(floor).size() > 0) {
            for (int person : dynamicQueues.get(floor)) {
                if (person > floor) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private static boolean arePeopleAtThisFloorWantingToGoDown(int floor, List<List<Integer>> dynamicQueues) {
        boolean result = false;
        if (dynamicQueues.get(floor).size() > 0) {
            for (int person : dynamicQueues.get(floor)) {
                if (person < floor) {
                    result = true;
                }
            }
        }
        return result;
    }

    private static boolean isSomeoneGettingOffAtThisFloor(int floor, List<Integer> peopleInTheLift) {
        return peopleInTheLift.contains(floor);
    }

    private static void stopAtThisFloor(int floor, List<Integer> result) {
        int lastFloor = result.isEmpty() ? -1 : result.get(result.size()-1);
        if (lastFloor != floor) {
            result.add(floor);
        }
    }

    private static void getPeopleOutOfTheLift(int floor, List<Integer> peopleInTheLift) {
        while (peopleInTheLift.contains(floor)) {
            peopleInTheLift.remove(new Integer(floor));
        }
    }

    private static void takePeopleThatWantToGoUp(int floor, List<Integer> queue, List<Integer> peopleInTheLift, int capacity) {
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()) {
            Integer person = iter.next();
            if (peopleInTheLift.size() < capacity && person > floor) {
                peopleInTheLift.add(person);
                iter.remove();
            }
        }
    }

    private static void takePeopleThatWantToGoDown(int floor, List<Integer> queue, List<Integer> peopleInTheLift, int capacity) {
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()) {
            Integer person = iter.next();
            if (peopleInTheLift.size() < capacity && person < floor) {
                peopleInTheLift.add(person);
                iter.remove();
            }
        }
    }
}