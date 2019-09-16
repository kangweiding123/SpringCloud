import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestList {
    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Map<String,List<String>> map = new HashMap<>();
        map.put("list",list);
        System.out.println(map.toString());
        list = new ArrayList<>();
        list.add("d");
        list.add("e");
        list.add("f");
        map.put("list1",list);
        System.out.println(map.toString());
    }
}
