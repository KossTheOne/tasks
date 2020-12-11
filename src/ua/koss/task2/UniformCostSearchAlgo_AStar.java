package ua.koss.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//simple A* algorithm
public class UniformCostSearchAlgo_AStar{

    public static void main(String[] args) throws IOException {
        makeATest();
    }

    public static void makeATest() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Node> cityList = new ArrayList();
        Map<String, Node> cityMap = new HashMap();

        int testNumber = Integer.parseInt(reader.readLine());

        // I`m not sure about this part, prob you want to see something else
        if (testNumber > 10) {
            System.out.println("Test number can not be less then 10");
            return;
        }
        // the end of not sure part )

        int cities = Integer.parseInt(reader.readLine());
        if (cities > 10000) {
            System.out.println("Amount of cities can not be more then 10000");
            return;
        } else {
            // first of all I make list of empty cities
            for (int j = 0; j < cities; j++) {
                cityList.add(new Node());
            }

            for (int j = 0; j < cities; j++) {

                String cityName = reader.readLine();

                Node currentNode = cityList.get(j); //on first iteration we get first city from prev list, etc.
                currentNode.setValue(cityName);

                int neighborsCount = Integer.parseInt(reader.readLine());

                if (neighborsCount > cityList.size()) {
                    System.out.println("Amount of neighbors can not be more then total city amount");
                    return;
                }

                for (int k = 0; k < neighborsCount; k++) {

                    String neighborCityInfo = reader.readLine();
                    String[] neighbor_Cost = neighborCityInfo.split(" ");
                    int indexOfTheCity = Integer.parseInt(neighbor_Cost[0]);
                    int costOfTheCity = Integer.parseInt(neighbor_Cost[1]);

                    if (j == indexOfTheCity - 1) {
                        System.out.println("Current city can not be neighbor to itself");
                        return;
                    }

                    currentNode.addAdjacenciesNode(
                            new Edge(
                                    cityList.get(indexOfTheCity - 1), // any number of city it is n-1 in cityList
                                    costOfTheCity
                            )
                    );
                }

            }
        }

        /*
        map for getting a city by name, we can also use stream ()

            cityList.stream()
            .filter(s -> s.getName().equals(abstractCityName))

         */
        for (Node node :
                cityList) {
            cityMap.put(node.getValue(), node);
        }


        int theNumberOfPathsToFind = Integer.parseInt(reader.readLine());
        if (theNumberOfPathsToFind > 100)
            return;
        List<Integer> costList = new ArrayList();
        for (int j = 0; j < theNumberOfPathsToFind; j++) {

            String cityPair = reader.readLine();
            String[] cPair = cityPair.split(" ");
            Node node1 = cityMap.get(cPair[0]);
            Node node2 = cityMap.get(cPair[1]);

            UniformCostSearch(node1,node2); // magic part with A* algorithm ))
            costList.add((int) node2.pathCost);
        }
        costList.forEach(System.out::println);

        reader.readLine();
        makeATest();
    }

    public static void UniformCostSearch(Node source, Node goal){

        source.pathCost = 0;
        PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                new Comparator<Node>(){

                    //override compare method
                    public int compare(Node i, Node j){
                        if(i.pathCost > j.pathCost){
                            return 1;
                        }

                        else if (i.pathCost < j.pathCost){
                            return -1;
                        }

                        else{
                            return 0;
                        }
                    }
                }
        );

        queue.add(source);
        Set<Node> explored = new HashSet<Node>();
        boolean found = false;

        //while frontier is not empty
        do{
            Node current = queue.poll();
            explored.add(current);

            //end if path is found
            if(current.value.equals(goal.value)){
                found = true;
            }

            for(Edge e: current.adjacencies){
                Node child = e.target;
                double cost = e.cost;

                //add node to queue if node has not been explored
                if(!explored.contains(child) && !queue.contains(child)){
                    child.pathCost = current.pathCost + cost;
                    child.parent = current;
                    queue.add(child);

                }

                //current path is shorter than previous path found
                else if((queue.contains(child))&&(child.pathCost>(current.pathCost+cost))){
                    child.parent=current;
                    child.pathCost = current.pathCost + cost;
                    queue.remove(child);
                    queue.add(child);
                }
            }
        }while(!queue.isEmpty()&& (found==false));
    }

    public static List<Node> printPath(Node target){
        List<Node> path = new ArrayList<Node>();
        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

}

class Node{

    public String value;
    public double pathCost;
    public List<Edge> adjacencies = new ArrayList();
    public Node parent;

    public void setValue(String value) {
        this.value = value;
    }

    public Node(){
    }

    public String toString(){
        return value;
    }

    public void addAdjacenciesNode(Edge edge){
        adjacencies.add(edge);
    }

    public String getValue() {
        return value;
    }
}

class Edge{
    public final double cost;
    public final Node target;

    public Edge(Node targetNode, double costVal){
        cost = costVal;
        target = targetNode;
    }
}
