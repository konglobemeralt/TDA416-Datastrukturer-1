package tva;

public class SLCWithGetTest {
    public static void main (String[] args){
        SLCWithGet<Integer> testList = new SLCWithGet<>();
        testList.add(10);
        System.out.println(testList.head.element);
        testList.add(5);
        System.out.println(testList.head.element);
        for(int i = 10; i<100; i=i+2){
            testList.add(i);
            System.out.println(i);
        }

        System.out.println(testList.toString());

        testList.add(null);
        System.out.println(testList.toString());
    }
}
