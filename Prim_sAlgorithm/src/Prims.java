/*
* İbrahim Güneş
* 22060655
* Prim's Algoritması Java uygulaması
*/
public class Prims {
    //n değişkeni graftaki düğüm sayısını tutmaktadır.
    private static final int n = 10;
    public static void main(String[] args) {
        Prims prims = new Prims();
        //Algoritmaya girdi olarak girecek olan komşuluk matrixi
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
        prims.prim(graph);
    }
    public void prim(int graph[][]){
        //n boyutunda oluşturulan her düğümün parentini tutan dizi
        int parent[] = new int[n];
        //n boyutunda oluşturulan her düğüme giden en küçük edge ağırlığını tutan dizi
        int value[] = new int[n];
        //Düğümün ziyaret edilip edilmediğini tutan boolean yapıda dizi
        Boolean visit[] = new Boolean[n];
        //Her düğümün ve o düğüme giden edgein başlangıç değerlerinin ayarlandığı kısım
        for (int i = 0; i < n; i++) {
            value[i] = Integer.MAX_VALUE;
            visit[i] = false;
        }
        //Başlangıç düğümüne giden edge ağırlığının ve parentın ayarlandığı kısım
        value[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < n-1; i++) {
            int u = minValue(value,visit);//En düşük değerli düğüm bulunur
            visit[u] = true;//O düğüm ziyatet edildi olarak işaretlenir
            for (int j = 0; j < n; j++) {
                // Eğer edge varsa, ziyaret edilmemişse ve değeri daha küçükse günceller.
                if (graph[u][j] != 0 && !visit[j] && graph[u][j] < value[j]){
                    parent[j] = u;
                    value[j] = graph[u][j];
                }
            }
        }
        printPrim(parent,graph);
    }
    //En düşük değerli düğümü bulan fonksiyon
    public int minValue(int value[], Boolean visit[]){
        int min = Integer.MAX_VALUE;
        int minNode = -1;
        //Her düğümü dolaşarak ziyaret edilmemiş ve düğüm değeri en düşük olan düğümü bulan kısım
        for (int i = 0; i < n; i++) {
            if (!visit[i] && value[i] < min) {
                min = value[i];
                minNode = i;
            }
        }
        return minNode;
    }
    //Prim's algoritmasına göre mstnin yazdırılması
    public void printPrim(int parent[],int graph[][]){
        System.out.println("Edge \tWeight");
        for (int i = 1; i < n; i++) {
            System.out.println(parent[i] + " - "+i+ "\t"+graph[i][parent[i]]);
        }
    }
}