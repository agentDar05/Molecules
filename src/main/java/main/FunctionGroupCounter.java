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
    public static int countPhenyl(MoleculeWithAdjacencyList target){
        MoleculeWithAdjacencyList phenyl = new MoleculeWithAdjacencyList();
        int c1 = phenyl.addAtom((byte) 6);
        int c2 = phenyl.addAtom((byte) 6);
        int c3 = phenyl.addAtom((byte) 6);
        int c4 = phenyl.addAtom((byte) 6);
        int c5 = phenyl.addAtom((byte) 6);
        int c6 = phenyl.addAtom((byte) 6);
        int h7 = phenyl.addAtom((byte) 1);
        int h8 = phenyl.addAtom((byte) 1);
        int h9 = phenyl.addAtom((byte) 1);
        int h10 = phenyl.addAtom((byte) 1);
        int h11 = phenyl.addAtom((byte) 1);
        phenyl.addBond(c3, c1, BondType.DOUBLE);
        phenyl.addBond(c1, c5, BondType.SINGLE);
        phenyl.addBond(c5, c6, BondType.DOUBLE);
        phenyl.addBond(c6, c4, BondType.SINGLE);
        phenyl.addBond(c4, c2, BondType.DOUBLE);
        phenyl.addBond(c2, c3, BondType.SINGLE);
        phenyl.addBond(c1, h7, BondType.SINGLE);
        phenyl.addBond(c2, h8, BondType.SINGLE);
        phenyl.addBond(c3, h9, BondType.SINGLE);
        phenyl.addBond(c4, h10, BondType.SINGLE);
        phenyl.addBond(c5, h11, BondType.SINGLE);
        return countSubgraphs(phenyl, target);
    }
    public static int countBenzeneRings(MoleculeWithAdjacencyList target){
        MoleculeWithAdjacencyList ring = new MoleculeWithAdjacencyList();
        int c1 = ring.addAtom((byte) 6);
        int c2 = ring.addAtom((byte) 6);
        int c3 = ring.addAtom((byte) 6);
        int c4 = ring.addAtom((byte) 6);
        int c5 = ring.addAtom((byte) 6);
        int c6 = ring.addAtom((byte) 6);
        int h7 = ring.addAtom((byte) 1);
        int h8 = ring.addAtom((byte) 1);
        int h9 = ring.addAtom((byte) 1);
        int h10 = ring.addAtom((byte) 1);
        int h11 = ring.addAtom((byte) 1);
        int h12 = ring.addAtom((byte) 1);
        ring.addBond(c3, c1, BondType.DOUBLE);
        ring.addBond(c1, c5, BondType.SINGLE);
        ring.addBond(c5, c6, BondType.DOUBLE);
        ring.addBond(c6, c4, BondType.SINGLE);
        ring.addBond(c4, c2, BondType.DOUBLE);
        ring.addBond(c2, c3, BondType.SINGLE);
        ring.addBond(c1, h7, BondType.SINGLE);
        ring.addBond(c2, h8, BondType.SINGLE);
        ring.addBond(c3, h9, BondType.SINGLE);
        ring.addBond(c4, h10, BondType.SINGLE);
        ring.addBond(c5, h11, BondType.SINGLE);
        ring.addBond(c6, h12, BondType.SINGLE);
        return countSubgraphs(ring, target);
    }
    public static int countAromaticRings(MoleculeWithAdjacencyList target) {
        MoleculeWithAdjacencyList ring = new MoleculeWithAdjacencyList();
        int c1 = ring.addAtom((byte) 6);
        int c2 = ring.addAtom((byte) 6);
        int c3 = ring.addAtom((byte) 6);
        int c4 = ring.addAtom((byte) 6);
        int c5 = ring.addAtom((byte) 6);
        int c6 = ring.addAtom((byte) 6);

        ring.addBond(c1, c2, BondType.AROMATIC);
        ring.addBond(c2, c3, BondType.AROMATIC);
        ring.addBond(c3, c4, BondType.AROMATIC);
        ring.addBond(c4, c5, BondType.AROMATIC);
        ring.addBond(c5, c6, BondType.AROMATIC);
        ring.addBond(c6, c1, BondType.AROMATIC);

        return countSubgraphs(ring, target);
    }
}