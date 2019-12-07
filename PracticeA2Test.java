import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PracticeA2Test {

    public PracticeA2Test(){
    }


    public String shortestPath(String a1, String a2, int index, Actor[] Actors){
        a1=a1.toLowerCase();
        a2=a2.toLowerCase();
        String path = a1+" -->  ";;
        if (!exists(a1, Actors)){
            return (a1+" is not an actor");
        }
        else if (!exists(a2, Actors)){
            return (a2+" is not an actor");
        }
        else {
            int a1Hash = java.lang.Math.abs(a1.hashCode());
            int a2Hash = java.lang.Math.abs(a2.hashCode());
            int a1i = a1Hash%Actors.length;
            int a2i = a2Hash%Actors.length;
            Actor b = Actors[a2i];
            while (!b.name.equals(a2)){
                b=b.next;
            }
            Actor c = Actors[a1i];
            while (!c.name.equals(a1)){
                c=c.next;
            }
            try {
                int i = 0;
                while (c.film[i]!=null&&i<c.film.length){
                    int j = 0;
                    while (b.film[j]!=null&&j<b.film.length){
                        if (c.film[i].title.equals(b.film[j].title)){
                            return path+" "+a2;
                        }
                        j++;
                    }
                    i++;
                }
            }
            catch (StackOverflowError e){
                return "no films together";
            }
            for (int i = 0;i<b.film.length;i++){
                String p = shortestPath(getActor(b.film[i].cast[i]),a2,index,Actors);
                if (!p.equals("no films together")){
                    return path+""+p;
                }
            }
        }
        return null;

    }

    public boolean exists(String name, Actor[] Actors){
        int nameHash = name.hashCode();
        nameHash = java.lang.Math.abs(nameHash);
        int index = nameHash%Actors.length;
        if (Actors[index]==null){
            return false;
        }
        Actor a = Actors[index];
        while (a!=null){
            if (a.name.equals(name)){
                return true;
            }
            a=a.next;
        }
        return false;
    }



    public String getActor(String s){
        try{
            int start = s.indexOf("name")+10;
            String cut = s.substring(start);
            int end = cut.indexOf(",")-2;
            String name = cut.substring(0,end);
            return name;

        }
        catch (NullPointerException e){
            return null;
        }
    }


    public boolean checkDup(Actor a, String name, Film film){
        if (a==null||a.name==null||name==null){
            return false;
        }
        if (a.name.equals(name)){
            a.addFilm(film);
            return true;
        }
        else {
            return checkDup(a.next,name,film);
        }
    }


    public static void main(String[] args) {


        String csvFile = args[0];
        BufferedReader br = null;
        String line = "";
        PracticeA2Test t = new PracticeA2Test();
        Actor[] Actors = new Actor[2027];


        try {

            br = new BufferedReader(new FileReader(csvFile));
            Node movie = new Node(null, null);
            while ((line = br.readLine()) != null) {
                try {
                    int comma1 = line.indexOf(",");
                    String cut1 = line.substring(comma1+1);
                    int comma2 = cut1.indexOf(",");
                    String movieTitle = cut1.substring(0,comma2);
                    if (!line.equals("movie_id,title,cast,crew")){
                        JSONReader j = new JSONReader();
                        String[] movieInfo = j.columnSplitter(line);
                        if (!movieInfo[0].trim().equals("[]")){
                            movieInfo[0] = movieInfo[0].replace("[Singing voice]","(Singing Voice)");
                            String[] cast = j.arraySplitter(movieInfo[0]);
                            movie = new Node(cast,movie);
                            Film film = movie.castInfo(cast, movieTitle);
                            for (int i = 0;i<cast.length;i++){
                                String name = (t.getActor(cast[i]));
                                if (name==null){
                                    i=cast.length;
                                }
                                if (name!=null){
                                    name = name.toLowerCase();
                                    int actorHash = name.hashCode();
                                    int index = actorHash%Actors.length;
                                    index = java.lang.Math.abs(index);
                                    if (Actors[index]==null){
                                        Actors[index] = new Actor(name, null);
                                    }
                                    else {
                                        if (Actors[index].name.equals(name)) {
                                            Actors[index].addFilm(film);
                                        } else {
                                            if (!t.checkDup(Actors[index], name, film)){
                                                Actors[index] = new Actor(name,Actors[index]);
                                                Actors[index].addFilm(film);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                catch (IllegalArgumentException e){
                    System.out.println(line);
                }



            }
            System.out.println(t.shortestPath("David Guy Brizan","Tom Cruise",0, Actors));
            System.out.println(t.shortestPath("Ted Raimi","Elizabeth Banks",0,Actors));
            System.out.println(t.shortestPath("Paul Dano","Asa Butterfield",0, Actors));
            System.out.println(t.shortestPath("Abigail Breslin","Asa Butterfield",0, Actors));
            System.out.println(t.shortestPath("Abigail Breslin","Hailee Steinfeld",0, Actors));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
