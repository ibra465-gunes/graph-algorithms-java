/*
* İbrahim Güneş
* 22060655
* Ford Fulkerson Algoritması uygulaması*/
import java.util.LinkedList;
public class Ford_Fulkerson_Algorithm {
    //Girdi olarak verilecek grafın düğüm sayısı
    private static final int v = 10;
    public static void main(String[] args) {
        Ford_Fulkerson_Algorithm fordFulkersonAlgorithm = new Ford_Fulkerson_Algorithm();
        //Grafı temsil eden komşuluk matrisi
        int graph[][] = new int[][] {
                { 0, 10, 5, 15, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 4, 0, 9, 15, 0, 0, 0, 0 },
                { 0, 0, 0, 4, 0, 8, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 30, 0, 0, 0 },
                { 0, 0, 0, 3, 0, 0, 12, 7 ,5 ,0 },
                { 0, 0, 6 ,7 ,10 ,10 ,7 ,10 ,6 ,7 },
                {20 ,6 ,9 ,8 ,9 ,8 ,20 ,6 ,9 ,8 },
                {6 ,7 ,5 ,4 ,3 ,4 ,3 ,6 ,7 ,5 },
                {3 ,8 ,5 ,8 ,3 ,1 ,4 ,2 ,3 ,1 },
                {2 ,2 ,2 ,2 ,2 ,2 ,2 ,2 ,2 ,2 }
        };
        System.out.println("Max Flow = "+fordFulkersonAlgorithm.fordFulkerson(graph,0,5));
    }
    //Ford Fulkerson algoritması için path bulan BFS algortiması
    public boolean bfs (int residualGraph[][], int source, int destination, int parent[]) {
        //Her vertex in ziyaret edilip edilmediğini tutacak olan visit dizisi
        boolean visit[] = new boolean[v];
        //Her vertex için başlangıç değeri false olarak ayarlanır.
        for (int i = 0; i < v; i++) {
            visit[i] = false;
        }
        //Komşuların kuyruğa atılacağı kuyruk yapısı Linklist ile oluşturulur.
        LinkedList<Integer> queue = new LinkedList<>();
        //Başlangıç vertex i kuyruğa eklenir. Ziyaret edildi olarak işaretlenir.
        // Önceki vertex i yani parenti yok anlamında -1 yapılır.
        queue.add(source);
        visit[source] = true;
        parent[source] = -1;
        //Kuyruk boş olmadığı sürece döngüye girilir.
        while (queue.size() != 0) {
            //Kuyruğun başındaki vertex kuyruktan çıkarılı.
            int a = queue.poll();
            //Kuyruktan çıkarılan vertex in komşuları ziyaret edilmediği sürece kuyruğa eklenir,
            // kuyruktan çıkarılan komşunun parent ı olarak ayarlanır. Komşu ziyaret edildi olarak işaretlenir.
            // Komşu hedef ise parent ı ayarlanır. Algoritma bitirilir.
            for (int i = 0; i < v; i++) {
                if (!visit[i] && residualGraph[a][i] > 0) {
                    if(i == destination){
                        parent[i] = a;
                        return true;
                    }
                    queue.add(i);
                    parent[i] = a;
                    visit[i] = true;
                }
            }
        }
        //Eğer kod bu kısma gelirse method false döndürür. Bu path yok anlamına gelir.
        return false;
    }
    //Ford Fulkerson algoritmasını uygulayan method.
    public int fordFulkerson(int graph[][],int source,int destination){
        //Başlangıçta maxFlow 0 olarak ayarlanır.
        int maxFlow = 0;
        //Üzerinde değişiklik yapılacak olan residualGraph oluşturulur.
        int residualGraph[][] = new int[v][v];
        //Path i tutacak olan parent dizisi oluşturulur.
        int parent[] = new int[v];
        //Girdi olarak girilen graph ın kopyası olacak şekilde residualGraph oluşturulur..
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }
        int a;
        //residualGraph da bulunan kenarların akış değerlerinin ayarlandığı kısım
        //Döngü path olduğu sürece çalışır.
        while(bfs(residualGraph,source,destination,parent)){
            //Başlangıçta flow max değer olarak ayarlanır.
            int flow = Integer.MAX_VALUE;
            //BFS den gelen path ile destination dan source gidecek şekilde tüm path
            // boyunca path üzerindeki vertex ile parenti arasındaki capacity flow ile karşılaştırılır.
            // Küçük olan flow olarak ayarlanır.
            for (int i = destination; i != source; i = parent[i]) {
                a = parent[i];
                flow = Math.min(flow,residualGraph[a][i]);
            }
            //Belirlenen throupht değeri olan flow üzerinden path üzerindeki kenarların capacity değerlerinden flow çıkarılır.
            // Ters yöndeki akış değerine flow eklenir. Path üzerindeki ayarlamalar bittikten sonra flow maxFlow a eklenir.
            for (int i = destination; i != source; i = parent[i]) {
                a = parent[i];
                residualGraph[a][i] = residualGraph[a][i] - flow;
                residualGraph[i][a] = residualGraph[i][a] + flow;
            }
            maxFlow = maxFlow + flow;
        }
        return maxFlow;
    }
}