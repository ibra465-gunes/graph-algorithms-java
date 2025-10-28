/*
 * İbrahim Güneş
 * 22060655
 * Kruskal's Algoritması Java uygulaması
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalAlgorithm {
    //n değişkeni algoritmaya girdi olarak girecek olan grafın düğümsayısını tutmaktadır.
    private static final int n = 10;
    public static void main(String[] args) {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm();
        //Algoritmaya girdi olarak girecek olan graf
        int graph[][] = new int[][] { {0, 3, 5, 6, 2, 7, 9, 8, 1, 4},
                {3, 0, 8, 1, 4, 2, 5, 6, 7, 9},
                {5, 8, 0, 9, 3, 1, 4, 2, 6, 7},
                {6, 1, 9, 0, 7, 5, 3, 4, 2, 8},
                {2, 4, 3, 7, 0, 6, 8, 9, 5, 1},
                {7, 2, 1, 5, 6, 0, 4, 3, 9, 8},
                {9, 5, 4, 3, 8, 4, 0, 1, 2, 6},
                {8, 6, 2, 4, 9, 3, 1, 0, 7, 5},
                {1, 7, 6, 2, 5, 9, 2, 7, 0, 3},
                {4, 9, 7, 8, 1, 8, 6, 5, 3, 0} };
        //Grafta bulunan edgeleri tutacak olan list yapısına edgelerin eklendiği kısım
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph[i][j] != 0) {
                    edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }
        //Kruskal algoritmasında kullanılacak olan edgelerin
        // küçükten büyüğe doğru sıralandığı kısım
        edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight-o2.weight;
            }
        });
        kruskalAlgorithm.kruskal(n,edges);
    }
    //Kruskal algoritmasını uygulayan kısım
    public void kruskal(int n, List<Edge> edges){
        //a değişkeni edgeleri tutan list üzerinde iteratör görevi görmekte
        int a = 0;
        //addEdge mst' ye eklenen kenar sayısını tutmakta
        int addEdge = 0;
        //Her düğümün alt küme bilgisini tutmakta
        Subset[] subsets = new Subset[n];
        //Mst'de kullanılacak kenarları tutmakta
        Edge[] kenarlar = new Edge[n];
        //Her düğümün alt küme bilgisi girilmekte
        for (int i = 0; i < n; i++) {
            subsets[i] = new Subset(i,0);
        }
        while (addEdge<n-1){
            //Sıradaki kenarı alır
            Edge nextEdge = edges.get(a);
            //O kenara bağlı olan düğümlerin kökünü bulup b ve c değişkenlerine atar
            int b = findParent(subsets, nextEdge.node1);
            int c = findParent(subsets, nextEdge.node2);
            //Eğer iki düğümün de kökleri eşit değilse döngü olmadığı anlamına gelir. Mst'ye ekler
            if (b != c){
                kenarlar[addEdge] = nextEdge;
                add(subsets,b,c);
                addEdge++;
            }
            //Bir sonraki edge için iteratör 1 arttırılır.
            a++;
        }
        //Mst yazdırılır.
        System.out.println("Edge \tWeight");
        for (int i = 0; i < addEdge; i++) {
            System.out.println(kenarlar[i].node1 + " - "
                    +  kenarlar[i].node2+ "\t"
                    + kenarlar[i].weight);
        }
    }
    //İki alt kümenin köklerini birleştiren fonksiyon.
    public void add(Subset[] subsets, int a, int b){
        //a düğümünün kökünü bulur.
        int parentA = findParent(subsets,a);
        //b düğümünün kökünü bulur.
        int parentB = findParent(subsets,b);
        //Alt küme değerlerine göre kümeler birleştirilir.
        if (subsets[parentB].value < subsets[parentA].value) {
            subsets[parentB].parent = parentA;
        }
        else if(subsets[parentB].value > subsets[parentA].value){
            subsets[parentA].parent = parentB;
        }
        else{
            subsets[parentB].parent = parentA;
            subsets[parentA].value++;
        }
    }
    //Bir düğümün kök ebeveynini bulan fonksiyon.
    public int findParent(Subset[] subsets, int a){
        if (subsets[a].parent== a){
            return subsets[a].parent;
        }
        else{
            subsets[a].parent = findParent(subsets,subsets[a].parent);
            return subsets[a].parent;
        }
    }
}
//Bu sınıf bir edge in iki ucundaki düğümü ve
// edge in ağırlığını tutmak için oluşturulmuştur.
class Edge{
    public int node1;
    public int node2;
    public int weight;

    public Edge(int node1, int node2, int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }
}
//Bu sınıf alt kümelerin ebeveyn bilgisini ve
// alt küme değerini tutmak için oluşturulmuştur.
class Subset{
    public int parent;
    public int value;

    public Subset(int parent, int value) {
        this.parent = parent;
        this.value = value;
    }
}
