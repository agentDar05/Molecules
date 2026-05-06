package main;

import main.VF2.MoleculeWithAdjacencyList;


import java.util.Arrays;

import static main.Parser.isFeasible;

class FunctionGroupCounter {
    public static int countSubgraphs(MoleculeWithAdjacencyList query, MoleculeWithAdjacencyList target) {
        int queryAtomCount = query.size();
        int targetAtomCount = target.size();
        int[] queryToTarget = new int[queryAtomCount];
        int[] targetToQuery = new int[targetAtomCount];
        int[] nextCandidate = new int[queryAtomCount];
        Arrays.fill(queryToTarget, -1);
        Arrays.fill(targetToQuery, -1);
        Arrays.fill(nextCandidate, 0);
        int queryDepth = 0;
        int count = 0;
        while (queryDepth >= 0) {
            if (queryDepth == queryAtomCount) {
                count++;
                queryDepth--;
                if (queryDepth >= 0) {
                    int mapped = queryToTarget[queryDepth];
                    targetToQuery[mapped] = -1;
                    queryToTarget[queryDepth] = -1;
                }
                continue;
            }
            boolean found = false;
            for (int targetCandidate = nextCandidate[queryDepth];
                 targetCandidate < targetAtomCount;
                 targetCandidate++) {
                nextCandidate[queryDepth] = targetCandidate + 1;
                if (targetToQuery[targetCandidate] != -1) continue;
                if (query.getAtom(queryDepth) != target.getAtom(targetCandidate)) continue;
                if (!isFeasible(query, target, queryToTarget, queryDepth, targetCandidate)) continue;
                queryToTarget[queryDepth] = targetCandidate;
                targetToQuery[targetCandidate] = queryDepth;
                queryDepth++;
                if (queryDepth < queryAtomCount)
                    nextCandidate[queryDepth] = 0;
                found = true;
                break;
            }
            if (!found) {
                nextCandidate[queryDepth] = 0;
                queryDepth--;
                if (queryDepth >= 0) {
                    int mapped = queryToTarget[queryDepth];
                    targetToQuery[mapped] = -1;
                    queryToTarget[queryDepth] = -1;
                }
            }
        }
        return count;
    }
    public static int countAlcohol(MoleculeWithAdjacencyList target){
        MoleculeWithAdjacencyList ohGroup = new MoleculeWithAdjacencyList();
        int c = ohGroup.addAtom((byte) 6);
        int o = ohGroup.addAtom((byte) 8);
        int h = ohGroup.addAtom((byte) 1);
        ohGroup.addBond(c, o);
        ohGroup.addBond(o, h);
        return countSubgraphs(ohGroup, target);
    }
    public static int countCarboxylic(MoleculeWithAdjacencyList target){
        MoleculeWithAdjacencyList coohGroup = new MoleculeWithAdjacencyList();
        int c = coohGroup.addAtom((byte) 6);
        int o = coohGroup.addAtom((byte) 8);
        int o2 = coohGroup.addAtom((byte) 8);
        int h = coohGroup.addAtom((byte) 1);
        coohGroup.addBond(c, o2, BondType.DOUBLE);
        coohGroup.addBond(c, o);
        coohGroup.addBond(o, h);
        return countSubgraphs(coohGroup, target);
    }
    public static int countAldehyde(MoleculeWithAdjacencyList target){
        MoleculeWithAdjacencyList aldehydeGroup = new MoleculeWithAdjacencyList();
        int c = aldehydeGroup.addAtom((byte) 6);
        int h = aldehydeGroup.addAtom((byte) 1);
        int o = aldehydeGroup.addAtom((byte) 8);
        aldehydeGroup.addBond(c, h);
        aldehydeGroup.addBond(c, o, BondType.DOUBLE);
        return countSubgraphs(aldehydeGroup, target);
    }
    public static int countNitrile(MoleculeWithAdjacencyList target){
        MoleculeWithAdjacencyList nitrileGroup = new MoleculeWithAdjacencyList();
        int c = nitrileGroup.addAtom((byte) 6);
        int n = nitrileGroup.addAtom((byte) 7);
        nitrileGroup.addBond(c, n, BondType.TRIPLE);
        return countSubgraphs(nitrileGroup, target);
    }
}