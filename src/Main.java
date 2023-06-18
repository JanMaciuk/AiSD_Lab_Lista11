import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    private static final ArrayList<OneWayLinkedListWithHead<City>> graph = new ArrayList<>(); // Lista sąsiedztwa
    private static final int numberOfCities = 5;
    public static void main(String[] args) {
        // Inicjalizuje listę sąsiedztwa
        for (int i = 0; i < numberOfCities; i++) {
            graph.add(new OneWayLinkedListWithHead<>());
            graph.get(i).add(new City(i));
        }
        // Dodaję drogi pomiędzy miastami
        addRoad(0, 1, 10);
        addRoad(0, 3, 30);
        addRoad(0, 4, 100);
        addRoad(1, 2, 50);
        addRoad(2, 4, 10);
        addRoad(3, 2, 20);
        addRoad(3, 4, 60);
        printGraph();
        DFS(0);
        dijkstra(0);


    }

    public static void addRoad(int city1, int city2, int roadLength) {
        graph.get(city1).get(graph.get(city1).size()-1).AddRoad(city2, roadLength);
        graph.get(city1).add(new City(city2));
    }


    public static void DFS(int a) {
        System.out.println("\nPrzegląd drzewa w głąb (DFS):");
        Stack<City> Stack = new Stack<>();
        Stack.add(graph.get(a).get(0)); // Dodaje miasto od którego zaczynam do stosu
        boolean[] visitedID = new boolean[numberOfCities];
        visitedID[a] = true; // Miasto startowe jest odwiedzone
        while (!Stack.isEmpty()) {
            City city = Stack.pop(); // Odwiedzam miasto ze stosu (ostatnio dodane)
            System.out.println(city.name());

            OneWayLinkedListWithHead<City> list = graph.get(city.ID); // Lista połączeń z tego miasta
            for (City connectedCity : list) { // Dodaje wszytskie nieodwiedzone miasta do których jest połączenie do stosu
                if (!visitedID[connectedCity.ID]) {
                    visitedID[connectedCity.ID] = true;
                    Stack.push(connectedCity);
                }
            }
        }
    }

    public static void printGraph() {
        System.out.println("\nLista sąsiedztwa:");
        for (int i = 0; i < numberOfCities; i++) {
            System.out.print(graph.get(i).get(0).name());
            OneWayLinkedListWithHead<City> roadsList = graph.get(i);

            if (roadsList.size() != 1) {
                System.out.print(" --> ");
            }
            // Przypadek w którym nie ma dróg do innych miast
            else {System.out.println(" - brak połączeń"); continue;}

            for (City city : roadsList) {
                if (city.ID == i) continue; // Pomijam połączenie miasta z samym sobą
                if (city.ID == roadsList.get(roadsList.size()-1).ID) System.out.print(city.name()); // Jeżeli to ostatnie miasto w liście to nie dodaje strzałki
                else System.out.print(city.name() + " --> ");
            }
            System.out.println();
        }
    }

    public static void dijkstra(int startCityID) {
        // Arraye przechowują informacje o odległości do miasta i o tym czy je już odwiedziłem.
        int[] distances = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities]; // Domyślnie wszystkie wartości są false, jak powinno być

        Arrays.fill(distances, Integer.MAX_VALUE); // Odległości początkowo ustawione na 'nieskończoność' (największe co się zmieści do int-a)
        distances[startCityID] = 0;                // Miasto startowe z oczywistych względów ma odległość 0

        // Znalezienie najkrótszych ścieżek
        for (int i = 0; i < numberOfCities; i++) {
            int minDistance = Integer.MAX_VALUE;      // Najmniejsza znaleziona odległość, na początku 'nieskończoność'
            int minIndex = -1;                        // Indeks miasta o najmniejszej odległości

            // Znalezienie wierzchołka o najmniejszej odległości spośród nieodwiedzonych miast
            for (int j = 0; j < numberOfCities; j++) {
                if (!visited[j] && distances[j] < minDistance) { // W pierwszej iteracji tylko miasto startowe ma mniejszą odległość od max.
                    minDistance = distances[j];
                    minIndex = j;
                }
            }

            if (minIndex == -1) { break; } // Brak połączenia do pozostałych nieodwiedzonych miast (żadnego nie znalazłem)

            visited[minIndex] = true;   // Odwiedzamy znalezione miasto

            // Aktualizacja odległości do sąsiednich wierzchołków
            OneWayLinkedListWithHead<City> roadsList = graph.get(minIndex);   // Lista sąsiedztwa odwiedzanego miasta, ma wszystkie drogi do innych miast
            for (City city : roadsList) {
                if (city.roadLength == Integer.MAX_VALUE) continue; // Pomijam miasta do których nie ma połączenia (nieskończoność)
                // Rozpatruje odległości wszystkich dróg wychodzących z odwiedzanego miasta
                if (!visited[city.nextCityID]) {    // Jeżeli nie odwiedziłem jeszcze kolejnego miasta to rozpatruję nową drogę do niego
                    int newDistance = distances[minIndex] + city.roadLength;    // Odległość do miasta w którym jestem + długość drogi do kolejnego miasta
                    if (newDistance < distances[city.nextCityID]) {
                        // Jeżeli nowa droga jest krótsza od poprzednio znalezionej to zapisuje ją jako nowy dystans.
                        distances[city.nextCityID] = newDistance;
                    }
                }
            }
        }

        // Printowanie wyników
        System.out.println();
        for (int i = 0; i < numberOfCities; i++) {
            if (distances[i] == 0) {
                System.out.println("Miasto startowe to " + graph.get(i).get(0).name());
            }
            else if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("Brak drogi z miasta startowego do Miasta " + graph.get(i).get(0).name()); // ach te polskie drogi, jednokierunkowa pomiędzy miastami :)
            } else {
                System.out.println(graph.get(i).get(0).name() + ", odległość od miasta startowego: " + distances[i]);
            }
        }
    }



}