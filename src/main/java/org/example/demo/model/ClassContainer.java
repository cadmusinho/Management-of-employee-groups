package main.java.org.example.demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    public Map<String, ClassEmployee> grupyPracownikow = new HashMap<>();

    public void addClass(String nazwaGrupy, int maxCapacity) {
        if (!grupyPracownikow.containsKey(nazwaGrupy)) {
            grupyPracownikow.put(nazwaGrupy, new ClassEmployee(nazwaGrupy, new ArrayList<>(), maxCapacity));
        }
    }

    public void removeClass(String nazwaGrupy) {
        grupyPracownikow.remove(nazwaGrupy);
    }

    public List<String> findEmpty() {
        List<String> emptyGroups = new ArrayList<>();
        for (Map.Entry<String, ClassEmployee> entry : grupyPracownikow.entrySet()) {
            if (entry.getValue().pracownicy.isEmpty()) {
                emptyGroups.add(entry.getKey());
            }
        }
        return emptyGroups;
    }

    public void summary() {
        for (Map.Entry<String, ClassEmployee> entry : grupyPracownikow.entrySet()) {
            ClassEmployee group = entry.getValue();
            int filled = group.pracownicy.size();
            int max = group.max;
            double percentage = ((double) filled / max) * 100;
            System.out.printf("Group: %s, Capacity: %.2f%% filled\n", entry.getKey(), percentage);
        }
    }

    public void removeEmployeeFromGroup(String groupName, Employee employee) {
        ClassEmployee group = grupyPracownikow.get(groupName);
        if (group != null) {
            group.removeEmployee(employee);
            System.out.println("Employee " + employee + " removed from group " + groupName);
        } else {
            System.out.println("Group " + groupName + " does not exist.");
        }
    }
}
