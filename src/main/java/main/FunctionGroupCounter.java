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
}