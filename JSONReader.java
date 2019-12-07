public class JSONReader {

    public JSONReader(){

    }
    public String[] columnSplitter(String line){
        JSONReader j = new JSONReader();
        String[] columns = new String[6];
        int splits = 0;
        int slice = line.indexOf("[");
        while (slice>0&&slice<line.length()){
            int stop = line.indexOf("]");
            if (stop<0||stop<slice){
                stop=line.length()-1;
            }
            if (line.substring(slice,stop+1).equals("[]")){
                columns[splits]=line.substring(slice,stop);
            }
            else {
                columns[splits]=line.substring(slice+1,stop-1);

            }
            splits++;
            if (splits>=columns.length){
                columns = j.growArray(columns);
            }
            line=line.substring(stop+1);
            slice=line.indexOf("[");
        }
        return columns;
    }

    public String[] growArray(String[] a){
        int newSize = a.length*2;
        String[] s = new String[newSize];
        for (int i = 0; i<a.length;i++){
            s[i]=a[i];
        }
        return s;
    }
    public String[] arraySplitter(String line){
        int splits = 0;
        JSONReader j = new JSONReader();
        String[] data = new String[6];
        int slice = line.indexOf("{");
        while (slice>=0){
            int stop = line.indexOf("}");
            if (stop>0){
                data[splits]=line.substring(slice+1,stop-1);
            }
            else {
                data[splits]=line.substring(slice+1);
                stop=line.length()-1;
            }
            splits++;
            if (splits>=data.length){
                data = j.growArray(data);
            }
            line=line.substring(stop+1);
            slice=line.indexOf("{");
        }
        return data;

    }


}
