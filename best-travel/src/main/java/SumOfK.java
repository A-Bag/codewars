import java.util.*;

public class SumOfK {

    public static Integer chooseBestSum(int t, int k, List<Integer> ls) {
        if (k <= ls.size()) {
            List<List<Integer>> listOfListsOfAllCombinations = SumOfK.powerList(ls);
            Integer result =  listOfListsOfAllCombinations.stream()
                    .filter(n -> n.size() == k)
                    //.map(SumOfK :: printValues)
                    .mapToInt(n -> n.stream().mapToInt(m -> m).sum())
                    .filter(n -> n <= t)
                    .max()
                    .orElse(0);

            //If tasks requires combinations without repeats the Sets should be used:
            /*Set<Set<Integer>> setOfSetsOfAllCombinations = SumOfK.powerSet(new HashSet<>(ls));
            Integer result =  setOfSetsOfAllCombinations.stream()
                    .filter(n -> n.size() == k)
                    //.map(SumOfK :: printValues)
                    .mapToInt(n -> n.stream().mapToInt(m -> m).sum())
                    .filter(n -> n <= t)
                    .max()
                    .orElse(0);*/
            return result == 0 ? null : result;
        } else {
            return null;
        }
    }

    public static List<Integer> printValues (List<Integer> list) {
        System.out.println("List: ");
        for (Integer i: list) {
            System.out.println(i);
        }
        return list;
    }

    public static Set<Set<Integer>> powerSet(Set<Integer> originalSet) {
        Set<Set<Integer>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }
        List<Integer> list = new ArrayList<>(originalSet);
        Integer head = list.get(0);
        Set<Integer> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<Integer> set : powerSet(rest)) {
            Set<Integer> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    public static List<List<Integer>> powerList(List<Integer> originalList) {
        List<List<Integer>> lists = new ArrayList<>();
        if (originalList.isEmpty()) {
            lists.add(new ArrayList<>());
            return lists;
        }
        Integer head = originalList.get(0);
        List<Integer> rest = new ArrayList<>(originalList.subList(1, originalList.size()));
        for (List<Integer> lst : powerList(rest)) {
            List<Integer> newList = new ArrayList<>();
            newList.add(head);
            newList.addAll(lst);
            lists.add(newList);
            lists.add(lst);
        }
        return lists;
    }
}
