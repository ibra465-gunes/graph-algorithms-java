/*
 * 22060655
 * İbrahim Güneş
 * BellmanFord Algoritması Uygulama*/
import java.util.ArrayList;
import java.util.List;

public class BelmanFordAlgorithm {
    //Girdi olarak verilecek grafın düğüm sayısı
    public static final int v = 10;
    public static void main(String[] args) {
        BelmanFordAlgorithm belmanFordAlgorithm = new BelmanFordAlgorithm();
        //Grafı temsil eden komşuluk matrisi
        int graph[][] = {
                {0, 6, 0, 1, 0, 0, 0, 0, 0, 0},
                {6, 0, 5, 2, 2, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 0, 4, 0, 0, 0, 0},
                {1, 2, 0, 0, 7, 0, 3, 0, 0, 0},
                {0, 2, 0, 7, 0, 0, 0, 4, 0, 0},
                {0, 0, 4, 0, 0, 0, 5, 0, 0, 0},
                {0, 0, 0, 3, 0, 5, 0, 0, 0, 6},
                {0, 0, 0, 0, 4, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 8},
                {0, 0, 0, 0, 0, 0, 6, 0, 8, 0}
        };
        //Edge tipinde List yapısı oluşturulur.
        List<Edge> edges = new ArrayList<>();
        //Grafda bulunan edge'ler listeye eklenir
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (graph[i][j] != 0) {
                    edges.add(new Edge(i,j,graph[i][j]));
                }
            }
        }
        belmanFordAlgorithm.BellmanFord(edges,0);
    }
    // Bellman-Ford algoritmasının uygulanması
    public void BellmanFord(List<Edge> edges, int src){
        //Vertex sınıfından nesne dizisi oluşturur.
        Vertex[] vertices = new Vertex[v];
        //Oluşturulan nesnelerin başlangıç değerleri ayarlanır.
        for (int i = 0; i < v; i++) {
            vertices[i] = new Vertex(Integer.MAX_VALUE);
        }
        //Başlangıç düğümünün mesafesi 0 olarak ayarlanır.
        vertices[src].value = 0;
        //Vertex sayısının bir eksiği kadar tüm edge'ler dolaşılır.
        for (int i = 0; i < v - 1; i++) {
            // Tüm edge'ler üzerinde gezilir.
            for (Edge edge : edges) {
                int u = edge.node1;
                int v = edge.node2;
                int weight = edge.weight;
                // Eğer daha kısa bir yol bulunursa mesafe güncellenir
                if (vertices[u].value != Integer.MAX_VALUE && vertices[u].value + weight < vertices[v].value) {
                    vertices[v].value = vertices[u].value + weight;
                }
            }
        }
        // Negatif döngü kontrolü için bir kere daha tüm edge'ler dolaşılır.
        for (Edge edge : edges) {
            int u = edge.node1;
            int v = edge.node2;
            int weight = edge.weight;
            // Eğer negatif döngü varsa uyarı verilir ve algoritma durur.
            if (vertices[u].value != Integer.MAX_VALUE && vertices[u].value + weight < vertices[v].value) {
                System.out.println("Negatif döngü var");
                return;
            }
        }
        print(vertices);
    }
    //Düğümlerin verilen başlangıç düğümüne göre mesafesini yazdıran method
    public void print(Vertex[] vertices){
        System.out.println("Vertex \tMesafe");
        for (int i = 0; i < v; i++) {
            System.out.println(i+"\t\t"+vertices[i].value);
        }
    }
}
//Vertex sınıfı
class Vertex{
    //Vertex'in mesafesini tutan yapı
    public int value;

    public Vertex(int value) {
        this.value = value;
    }
}
//Edge sınıfı
class Edge{
    //Edge'nin başlangıç vertex'i
    public int node1;
    //Edge'nin bitiş vertex'i
    public int node2;
    //Edge'nin ağırlığı
    public int weight;

    public Edge(int node1, int node2, int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }
}
