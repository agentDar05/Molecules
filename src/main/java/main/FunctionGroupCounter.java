package main;

import main.VF2.MoleculeWithAdjacencyList;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static main.Parser.isFeasible;

class FunctionGroupCounter {
    public static MoleculeWithAdjacencyList OH_GROUP;
    public static MoleculeWithAdjacencyList COOH_GROUP;
    public static MoleculeWithAdjacencyList FORMIC_ACID;
    public static MoleculeWithAdjacencyList ALDEHYDE_GROUP;
    public static MoleculeWithAdjacencyList NITRILE_GROUP;
    public static MoleculeWithAdjacencyList PHENYL_GROUP;
    public static MoleculeWithAdjacencyList RING;
    public static MoleculeWithAdjacencyList AROMATIC_RING;
    public static MoleculeWithAdjacencyList KETO_TAUTOMER;
    public static MoleculeWithAdjacencyList ENOL_TAUTOMER;
    /***
     * Alcohol,
     * Carboxylic,
     * Aldehyde,
     * Nitrile,
     * Phenyl,
     * Ring,
     * Aromatic ring,
     * Keto tautomer,
     * Enol tautomer
     */

    public static boolean[] functionGroups = new boolean[8];

    static{
        {
            InputStream is_oh = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("oh_group.mol");
            try {
                OH_GROUP = (MoleculeWithAdjacencyList) MolV3000.read(is_oh);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_cooh = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("cooh_group.mol");
            try {
                COOH_GROUP = (MoleculeWithAdjacencyList) MolV3000.read(is_cooh);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_formic_acid = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("formic_acid.mol");
            try {
                FORMIC_ACID = (MoleculeWithAdjacencyList) MolV3000.read(is_formic_acid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_aldehyde = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("aldehyde_group.mol");
            try {
                ALDEHYDE_GROUP = (MoleculeWithAdjacencyList) MolV3000.read(is_aldehyde);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_nitrile = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("nitrile_group.mol");
            try {
                NITRILE_GROUP = (MoleculeWithAdjacencyList) MolV3000.read(is_nitrile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_phenyl = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("phenyl_group.mol");
            try {
                PHENYL_GROUP = (MoleculeWithAdjacencyList) MolV3000.read(is_phenyl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_ring = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("ring.mol");
            try {
                RING = (MoleculeWithAdjacencyList) MolV3000.read(is_ring);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_arom_ring = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("aromatic_ring.mol");
            try {
                AROMATIC_RING = (MoleculeWithAdjacencyList) MolV3000.read(is_arom_ring);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_keto = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("keto_tautomer.mol");
            try {
                KETO_TAUTOMER = (MoleculeWithAdjacencyList) MolV3000.read(is_keto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_enol = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("enol_tautomer.mol");
            try {
                ENOL_TAUTOMER = (MoleculeWithAdjacencyList) MolV3000.read(is_enol);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean[] countFunctionGroups (MoleculeWithAdjacencyList target){
        Arrays.fill(functionGroups, false);
        functionGroups[0] = countAlcohol(target) >=1;
        functionGroups[1] = countCarboxylic(target) >=1;
        functionGroups[2] = countAldehyde(target) >=1;
        functionGroups[3] = countNitrile(target) >=1;
        functionGroups[4] = countPhenyl(target) >=1;
        functionGroups[5] = countBenzeneRings(target) >=1;
        functionGroups[6] = countAromaticRings(target) >=1;
        if (countKeto(target) >=1){
            functionGroups[7] = true;
            functionGroups[8] = true;
        }
        if (countEnol(target) >=1){
            functionGroups[7] = true;
            functionGroups[8] = true;
        }

        return functionGroups;
    }
    public static int countSubgraphs(MoleculeWithAdjacencyList query, MoleculeWithAdjacencyList target) {
        int queryAtomCount = query.size();
        if(queryAtomCount==0){
            throw new IllegalArgumentException("Query is empty");
        }
        int targetAtomCount = target.size();
        if(targetAtomCount==0){
            throw new IllegalArgumentException("Target is empty");
        }
        if(queryAtomCount>targetAtomCount){
            throw new  IllegalArgumentException("Query is bigger than target, query size: " + queryAtomCount + ", target size: " + targetAtomCount);
        }
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
                int queryAtom = query.getAtom(queryDepth);
                int targetAtom = target.getAtom(targetCandidate);
                if (queryAtom != 0 && queryAtom != targetAtom) continue;
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
        return countSubgraphs(OH_GROUP, target);
    }
    public static int countCarboxylic(MoleculeWithAdjacencyList target){
        if(countSubgraphs(FORMIC_ACID, target) > 0){
            return 0;
        }
        return countSubgraphs(COOH_GROUP, target);
    }
    public static int countAldehyde(MoleculeWithAdjacencyList target){
        return countSubgraphs(ALDEHYDE_GROUP, target);
    }
    public static int countNitrile(MoleculeWithAdjacencyList target){
        return countSubgraphs(NITRILE_GROUP, target);
    }
    public static int countPhenyl(MoleculeWithAdjacencyList target){
        return countSubgraphs(PHENYL_GROUP, target);
    }
    public static int countBenzeneRings(MoleculeWithAdjacencyList target){
        return countSubgraphs(RING, target);
    }
    public static int countAromaticRings(MoleculeWithAdjacencyList target) {
        return countSubgraphs(AROMATIC_RING, target);
    }
    public static int countKeto(MoleculeWithAdjacencyList target) {
        return countSubgraphs(KETO_TAUTOMER, target);
    }
    public static int countEnol(MoleculeWithAdjacencyList target) {
        return countSubgraphs(ENOL_TAUTOMER, target);
    }

}