public class Actor {

    String name;
    Film[] film = new Film[20];
    int films;
    Actor next;

    Actor(String name, Actor next){
        this.name=name;
        this.next=next;
        films=0;
    }
    public  void addFilm(Film f){
        film[films] = f;
        films++;
        if (films>=film.length){
            film = growArray(film);
        }
    }
    public Film[] growArray(Film[] film){
        Film[] temp = new Film[film.length*2];
        for (int i =0;i<film.length;i++){
            temp[i]=film[i];
        }
        return temp;
    }

}
