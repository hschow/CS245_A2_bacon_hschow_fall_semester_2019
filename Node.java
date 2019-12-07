public class Node {

    String[] data;
    Node next;

    public Node(String[] data, Node next){
        this.next=next;
        this.data=data;
    }


    public Film castInfo(String[] cast, String title){
        Film film = new Film(title, cast);
        return film;
    }



}
