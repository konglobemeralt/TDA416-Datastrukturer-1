package tva;

public class SplayTest {
    public static void main(String[] args){
        SplayWithGet<Integer> tree = new SplayWithGet<>();
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(100);
        tree.add(60);
        tree.add(10);
        tree.add(30);

        System.out.println(tree.toString());

        tree.get(10);

        System.out.println(tree.toString());
        

        tree.get(10000);
    }

}
