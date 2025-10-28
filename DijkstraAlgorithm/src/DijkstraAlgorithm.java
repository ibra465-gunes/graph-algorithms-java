/*
* 22060655
* İbrahim Güneş
* Dijkstra Algoritması Uygulama*/
public class DijkstraAlgorithm {
    //Girdi olarak verilecek grafın düğüm sayısı
    public static final int v = 10;

    public static void main(String[] args) {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
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
        //Grafın negatif ağırlık içerip içermediğini kontrol eden kısım
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (graph[i][j] < 0) {
                    throw new IllegalArgumentException("Graf negatif ağırlık içeremez.");
                }
            }
        }
        dijkstraAlgorithm.Dijkstra(graph, 0);
    }
    //Dijkstra algortimasını uygulayan method
    public void Dijkstra(int[][] graph, int src) {
        //Vertex sınıfından nesne dizisi oluşturur.
        Vertex[] vertices = new Vertex[v];
        //Oluşturulan nesnelerin başlangıç değerleri ayarlanır.
        for (int i = 0; i < v; i++) {
            vertices[i] = new Vertex(Integer.MAX_VALUE, false);
        }
        //Başlangıç düğümünün mesafesi 0 olarak ayarlanır.
        vertices[src].value = 0;
        //Her düğüm gezilir
        for (int i = 0; i < v - 1; i++) {
            //Ziyaret edilmemiş en küçük mesafeli düğüm seçilir
            int a = minMesafe(vertices);
            //Ziyaret edilmemiş en küçük mesafeli düğüm ziyaret edildi olarak işaretlenir
            vertices[a].visit = true;
            //Ziyaret edilen düğümün komşu değerleri güncellenir.
            for (int j = 0; j < v; j++) {
                if (!vertices[j].visit && graph[a][j] != 0 && (graph[a][j] + vertices[a].value) < vertices[j].value && vertices[a].value != Integer.MAX_VALUE) {
                    vertices[j].value = graph[a][j] + vertices[a].value;
                }
            }
        }
        print(vertices);
    }
    //Düğümlerin verilen başlangıç düğümüne göre mesafesini yazdıran method
    public void print(Vertex[] vertices) {
        System.out.println("Vertex" + "\tMesafe");
        for (int i = 0; i < v; i++) {
            System.out.println(i + "\t\t" + vertices[i].value);
        }
    }
    //Ziyaret edilmemiş minimum mesafeli düğümü bulan method
    public int minMesafe(Vertex[] vertices) {
        int min = Integer.MAX_VALUE;
        int minVertex = -1;
        for (int i = 0; i < v; i++) {
            if (!vertices[i].visit && vertices[i].value < min) {
                min = vertices[i].value;
                minVertex = i;
            }
        }
        return minVertex;
    }
}
//Vertex sınıfı
class Vertex {
    //Vertex'in mesafesini tutan yapı
    public int value;
    //Vertex'in ziyaret edilip edilmediğini tutan yapı
    public boolean visit;

    public Vertex(int value, boolean visit) {
        this.value = value;
        this.visit = visit;
    }
}