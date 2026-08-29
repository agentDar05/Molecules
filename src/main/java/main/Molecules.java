package main;
import java.util.*;
public class Molecules {
    Map<String, List<String>> bonds = new HashMap<>();
    public void addAtom(String atom) {
        if(!bonds.containsKey(atom)) {
            bonds.put(atom, new ArrayList<>());
        }
    }
    public void addBond(String atom1, String atom2) {
        addAtom(atom1);
        addAtom(atom2);
        bonds.get(atom1).add(atom2);
        bonds.get(atom2).add(atom1);
    }
    public void dfs(String start){
        Set<String> visited = new HashSet<>();
        dfsStep(start, visited);
    }
    public void dfsStep(String atom, Set<String> visited){
        if(visited.contains(atom)){
            return;
        }
        visited.add(atom);
        System.out.println(atom);
        for(String s: bonds.get(atom)){
            dfsStep(s, visited);
        }
    }
    public void bfs(String start){
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        visited.add(start);
        q.add(start);
        while(!q.isEmpty()){
            String atom = q.remove();
            System.out.println(atom);
            for(String s: bonds.get(atom)){
                if(!visited.contains(s)){
                    visited.add(s);
                    q.add(s);
                }
            }
        }
    }

}
class Main{
    public static void main(String[] args){
                Molecules m = new Molecules();
                m.addBond("O", "H1");
                m.addBond("O", "H2");

                System.out.println("DFS:");
                m.dfs("O");

                System.out.println("BFS:");
                m.bfs("O");
           }
}