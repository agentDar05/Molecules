package main;

import main.VF2.MoleculeWithAdjacencyList;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static main.Parser.isFeasible;

class FunctionGroupCounter {
    public static MoleculeWithAdjacencyList OH_GROUP;
    public static MoleculeWithAdjacencyList COOH_GROUP;
    public static MoleculeWithAdjacencyList OXALIC_ACID;
    public static MoleculeWithAdjacencyList ALDEHYDE_GROUP;
    public static MoleculeWithAdjacencyList NITRILE_GROUP;
    public static MoleculeWithAdjacencyList PHENYL_GROUP;
    public static MoleculeWithAdjacencyList RING;
    public static MoleculeWithAdjacencyList AROMATIC_RING;
    public static MoleculeWithAdjacencyList KETO_TAUTOMER;
    public static MoleculeWithAdjacencyList ENOL_TAUTOMER;
    public static MoleculeWithAdjacencyList PRIMARY_AMINE;
    public static MoleculeWithAdjacencyList SECONDARY_AMINE;
    public static MoleculeWithAdjacencyList TERTIARY_AMINE;
    public static MoleculeWithAdjacencyList PRIMARY_AROMATIC_AMINE;
    public static MoleculeWithAdjacencyList SECONDARY_AROMATIC_AMINE;
    public static MoleculeWithAdjacencyList TERTIARY_AROMATIC_AMINE;


    /***
     * Alcohol,
     * Carboxylic,
     * Aldehyde,
     * Nitrile,
     * Phenyl,
     * Ring,
     * Aromatic ring,
     * Keto tautomer,
     * Enol tautomer,
     * Primary amine,
     * Secondary amine,
     * Tertiary amine
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
            InputStream is_oxalic_acid = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("oxalic_acid.mol");
            try {
                OXALIC_ACID = (MoleculeWithAdjacencyList) MolV3000.read(is_oxalic_acid);
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
        {
            InputStream is_primary = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("primary_amine.mol");
            try {
                PRIMARY_AMINE = (MoleculeWithAdjacencyList) MolV3000.read(is_primary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_secondary = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("secondary_amine.mol");
            try {
                SECONDARY_AMINE = (MoleculeWithAdjacencyList) MolV3000.read(is_secondary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_tertiary = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("tertiary_amine.mol");
            try {
                TERTIARY_AMINE = (MoleculeWithAdjacencyList) MolV3000.read(is_tertiary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_arom_primary = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("primary_aromatic_amine.mol");
            try {
                PRIMARY_AROMATIC_AMINE = (MoleculeWithAdjacencyList) MolV3000.read(is_arom_primary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_arom_secondary = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("secondary_aromatic_amine.mol");
            try {
                SECONDARY_AROMATIC_AMINE = (MoleculeWithAdjacencyList) MolV3000.read(is_arom_secondary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {
            InputStream is_arom_tertiary = FunctionGroupCounter.class
                    .getClassLoader()
                    .getResourceAsStream("tertiary_aromatic_amine.mol");
            try {
                TERTIARY_AROMATIC_AMINE = (MoleculeWithAdjacencyList) MolV3000.read(is_arom_tertiary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean[] findFunctionGroups (MoleculeWithAdjacencyList target){
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
        Set<Integer> usedAtoms = new HashSet<>();
        Set<String> foundMappings = new HashSet<>();
        int queryDepth = 0;
        int count = 0;
        while (queryDepth >= 0) {
            if (queryDepth == queryAtomCount) {
                int[] mapping = queryToTarget.clone();
                boolean used = false;
                for (int atom : mapping) {
                    if (usedAtoms.contains(atom)) {
                        used = true;
                        break;
                    }
                }
                if (!used) {
                    for (int atom : mapping) {usedAtoms.add(atom);}
                    Arrays.sort(mapping);
                    String mappingKey = Arrays.toString(mapping);
                    if (foundMappings.add(mappingKey))
                        count++;
                }
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
        return countSubgraphs(target, OH_GROUP);
    }
    public static int countCarboxylic(MoleculeWithAdjacencyList target){
        if(countSubgraphs(OXALIC_ACID, target) > 0){
            return 0;
        }
        return countSubgraphs(COOH_GROUP, target);
    }
    public static int countAldehyde(MoleculeWithAdjacencyList target){
        return countSubgraphs(target, ALDEHYDE_GROUP);
    }
    public static int countNitrile(MoleculeWithAdjacencyList target){
        return countSubgraphs(target, NITRILE_GROUP);
    }
    public static int countPhenyl(MoleculeWithAdjacencyList target){
        return countSubgraphs(target, PHENYL_GROUP);
    }
    public static int countBenzeneRings(MoleculeWithAdjacencyList target){
        return countSubgraphs(target, RING);
    }
    public static int countAromaticRings(MoleculeWithAdjacencyList target) {
        return countSubgraphs(target, AROMATIC_RING);
    }
    public static int countKeto(MoleculeWithAdjacencyList target) {
        return countSubgraphs(target, KETO_TAUTOMER);
    }
    public static int countEnol(MoleculeWithAdjacencyList target) {
        return countSubgraphs(target, ENOL_TAUTOMER);
    }

    public static int isPrimaryAmine(MoleculeWithAdjacencyList target) {
        return countSubgraphs(target, PRIMARY_AMINE);
    }
    public static int isSecondaryAmine(MoleculeWithAdjacencyList target) {
        return countSubgraphs(target, SECONDARY_AMINE);
    }
    public static int isTertiaryAmine(MoleculeWithAdjacencyList target) {
        return countSubgraphs(target, TERTIARY_AMINE);
    }
    public static int isPrimaryAromaticAmine(MoleculeWithAdjacencyList target){
        return countSubgraphs(target, PRIMARY_AROMATIC_AMINE);
    };
    public static int isSecondaryAromaticAmine(MoleculeWithAdjacencyList target){
        System.out.println("SECONDARY QUERY:");
        for (int i = 0; i < SECONDARY_AROMATIC_AMINE.size(); i++) {
            System.out.println(i + ": " + SECONDARY_AROMATIC_AMINE.getAtom(i));
        }

        return countSubgraphs(target, SECONDARY_AROMATIC_AMINE);
    }
    public static int isTertiaryAromaticAmine(MoleculeWithAdjacencyList target){
        return countSubgraphs(target, TERTIARY_AROMATIC_AMINE);
    };

}