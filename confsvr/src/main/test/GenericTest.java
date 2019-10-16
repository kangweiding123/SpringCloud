import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class GenericTest {

    private Map<String, Integer> score;

    @Test
    public static void main(String args[]) throws Exception {
        Class<GenericTest> clazz = GenericTest.class;

        Field field = clazz.getDeclaredField("score");
//        通过getDeclaredField可以获得成员变量，但是对于Map来说，仅仅可以知道它是个Map，无法知道键值对各自的数据类型

        Class<?> a = field.getType();
//        获得field的数据类型，是Map

        System.out.println("score的类型是" + a);


        Type gType = field.getGenericType();
//        获得field的泛型类型

//        如果gType是ParameterizedType对象（参数化）
        if (gType instanceof ParameterizedType) {

            ParameterizedType pType = (ParameterizedType) gType;
//            就把它转换成ParameterizedType对象

            Type rType = pType.getRawType();
            System.out.println(rType);
//            获得原始数据类型

            Type[] tArgs = pType.getActualTypeArguments();
//            获得泛型类型的泛型参数（实际类型参数)

            System.out.println("泛型的类型是： ");
            for (int i = 0; i < tArgs.length; i++) {
                System.out.println("第" + i + "个泛型类型是： " + tArgs[i]);
            }
        } else {
            System.out.println("出错！！！");
        }
    }
}