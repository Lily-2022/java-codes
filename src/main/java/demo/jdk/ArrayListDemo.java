package demo.jdk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayListDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(10);
        list.set(1, "AAAA");
        list.add(2, "BBB");
        list.get(1);
    }

}
