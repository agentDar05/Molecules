package main;

import main.VF2.MoleculeWithAdjacencyList;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static main.Parser.isFeasible;

class FunctionGroupCounter {
    public static final MoleculeWithAdjacencyList OH_GROUP = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList COOH_GROUP = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList ALDEHYDE_GROUP = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList NITRILE_GROUP = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList PHENYL_GROUP = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList RING = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList AROMATIC_RING = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList KETO_TAUTOMER = new MoleculeWithAdjacencyList();
    public static final MoleculeWithAdjacencyList ENOL_TAUTOMER = new MoleculeWithAdjacencyList();

    static{
        int c_oh_group = OH_GROUP.addAtom((byte) 6);
        int o_oh_group = OH_GROUP.addAtom((byte) 8);
        int h_oh_group = OH_GROUP.addAtom((byte) 1);
        OH_GROUP.addBond(c_oh_group, o_oh_group);
        OH_GROUP.addBond(o_oh_group, h_oh_group);

        int c_cooh_group = COOH_GROUP.addAtom((byte) 6);
        int o_cooh_group = COOH_GROUP.addAtom((byte) 8);
        int o2_cooh_group = COOH_GROUP.addAtom((byte) 8);
        int h_cooh_group = COOH_GROUP.addAtom((byte) 1);
        COOH_GROUP.addBond(c_cooh_group, o2_cooh_group, BondType.DOUBLE);
        COOH_GROUP.addBond(c_cooh_group, o_cooh_group);
        COOH_GROUP.addBond(o_cooh_group, h_cooh_group);

        int c_aldehyde_group = ALDEHYDE_GROUP.addAtom((byte) 6);
        int h_aldehyde_group = ALDEHYDE_GROUP.addAtom((byte) 1);
        int o_aldehyde_group = ALDEHYDE_GROUP.addAtom((byte) 8);
        ALDEHYDE_GROUP.addBond(c_aldehyde_group, h_aldehyde_group);
        ALDEHYDE_GROUP.addBond(c_aldehyde_group, o_aldehyde_group, BondType.DOUBLE);

        int c_nitrile = NITRILE_GROUP.addAtom((byte) 6);
        int n_nitrile = NITRILE_GROUP.addAtom((byte) 7);
        NITRILE_GROUP.addBond(c_nitrile, n_nitrile, BondType.TRIPLE);

        int c1_phenyl = PHENYL_GROUP.addAtom((byte) 6);
        int c2_phenyl = PHENYL_GROUP.addAtom((byte) 6);
        int c3_phenyl = PHENYL_GROUP.addAtom((byte) 6);
        int c4_phenyl = PHENYL_GROUP.addAtom((byte) 6);
        int c5_phenyl = PHENYL_GROUP.addAtom((byte) 6);
        int c6_phenyl = PHENYL_GROUP.addAtom((byte) 6);
        int h7_phenyl = PHENYL_GROUP.addAtom((byte) 1);
        int h8_phenyl = PHENYL_GROUP.addAtom((byte) 1);
        int h9_phenyl = PHENYL_GROUP.addAtom((byte) 1);
        int h10_phenyl = PHENYL_GROUP.addAtom((byte) 1);
        int h11_phenyl = PHENYL_GROUP.addAtom((byte) 1);
        PHENYL_GROUP.addBond(c3_phenyl, c1_phenyl, BondType.DOUBLE);
        PHENYL_GROUP.addBond(c1_phenyl, c5_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c5_phenyl, c6_phenyl, BondType.DOUBLE);
        PHENYL_GROUP.addBond(c6_phenyl, c4_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c4_phenyl, c2_phenyl, BondType.DOUBLE);
        PHENYL_GROUP.addBond(c2_phenyl, c3_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c1_phenyl, h7_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c2_phenyl, h8_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c3_phenyl, h9_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c4_phenyl, h10_phenyl, BondType.SINGLE);
        PHENYL_GROUP.addBond(c5_phenyl, h11_phenyl, BondType.SINGLE);

        int c1_ring = RING.addAtom((byte) 6);
        int c2_ring = RING.addAtom((byte) 6);
        int c3_ring = RING.addAtom((byte) 6);
        int c4_ring = RING.addAtom((byte) 6);
        int c5_ring = RING.addAtom((byte) 6);
        int c6_ring = RING.addAtom((byte) 6);
        int h7_ring = RING.addAtom((byte) 1);
        int h8_ring = RING.addAtom((byte) 1);
        int h9_ring = RING.addAtom((byte) 1);
        int h10_ring = RING.addAtom((byte) 1);
        int h11_ring = RING.addAtom((byte) 1);
        int h12_ring = RING.addAtom((byte) 1);
        RING.addBond(c3_ring, c1_ring, BondType.DOUBLE);
        RING.addBond(c1_ring, c5_ring, BondType.SINGLE);
        RING.addBond(c5_ring, c6_ring, BondType.DOUBLE);
        RING.addBond(c6_ring, c4_ring, BondType.SINGLE);
        RING.addBond(c4_ring, c2_ring, BondType.DOUBLE);
        RING.addBond(c2_ring, c3_ring, BondType.SINGLE);
        RING.addBond(c1_ring, h7_ring, BondType.SINGLE);
        RING.addBond(c2_ring, h8_ring, BondType.SINGLE);
        RING.addBond(c3_ring, h9_ring, BondType.SINGLE);
        RING.addBond(c4_ring, h10_ring, BondType.SINGLE);
        RING.addBond(c5_ring, h11_ring, BondType.SINGLE);
        RING.addBond(c6_ring, h12_ring, BondType.SINGLE);

        int c1_aromatic = AROMATIC_RING.addAtom((byte) 6);
        int c2_aromatic = AROMATIC_RING.addAtom((byte) 6);
        int c3_aromatic = AROMATIC_RING.addAtom((byte) 6);
        int c4_aromatic = AROMATIC_RING.addAtom((byte) 6);
        int c5_aromatic = AROMATIC_RING.addAtom((byte) 6);
        int c6_aromatic = AROMATIC_RING.addAtom((byte) 6);
        AROMATIC_RING.addBond(c1_aromatic, c2_aromatic, BondType.AROMATIC);
        AROMATIC_RING.addBond(c2_aromatic, c3_aromatic, BondType.AROMATIC);
        AROMATIC_RING.addBond(c3_aromatic, c4_aromatic, BondType.AROMATIC);
        AROMATIC_RING.addBond(c4_aromatic, c5_aromatic, BondType.AROMATIC);
        AROMATIC_RING.addBond(c5_aromatic, c6_aromatic, BondType.AROMATIC);
        AROMATIC_RING.addBond(c6_aromatic, c1_aromatic, BondType.AROMATIC);

        int c1_enol = ENOL_TAUTOMER.addAtom((byte) 6); // 1
        int c2_enol = ENOL_TAUTOMER.addAtom((byte) 6); // 2
        int o_enol = ENOL_TAUTOMER.addAtom((byte) 8); // 3
        int any1_enol = ENOL_TAUTOMER.addAtom((byte) 0); // 4
        int any2_enol = ENOL_TAUTOMER.addAtom((byte) 0);// 5
        int any3_enol = ENOL_TAUTOMER.addAtom((byte) 0); // 6
        int h_enol = ENOL_TAUTOMER.addAtom((byte) 1); // 7
        ENOL_TAUTOMER.addBond(c1_enol, c2_enol, BondType.DOUBLE);
        ENOL_TAUTOMER.addBond(c1_enol, o_enol, BondType.SINGLE);
        ENOL_TAUTOMER.addBond(c1_enol, any1_enol, BondType.SINGLE);
        ENOL_TAUTOMER.addBond(c2_enol, any2_enol, BondType.SINGLE);
        ENOL_TAUTOMER.addBond(c2_enol, any3_enol, BondType.SINGLE);
        ENOL_TAUTOMER.addBond(o_enol, h_enol, BondType.SINGLE);

        int c1_keto = KETO_TAUTOMER.addAtom((byte) 6); // 1
        int c2_keto = KETO_TAUTOMER.addAtom((byte) 6); // 2
        int o_keto = KETO_TAUTOMER.addAtom((byte) 8); // 3
        int any1_keto = KETO_TAUTOMER.addAtom((byte) 0); // 4
        int any2_keto = KETO_TAUTOMER.addAtom((byte) 0);// 5
        int any3_keto = KETO_TAUTOMER.addAtom((byte) 0); // 6
        int h_keto = KETO_TAUTOMER.addAtom((byte) 1); // 7
        KETO_TAUTOMER.addBond(c1_keto, c2_keto, BondType.SINGLE);
        KETO_TAUTOMER.addBond(c1_keto, o_keto, BondType.DOUBLE);
        KETO_TAUTOMER.addBond(c1_keto, any1_keto, BondType.SINGLE);
        KETO_TAUTOMER.addBond(c2_keto, any2_keto, BondType.SINGLE);
        KETO_TAUTOMER.addBond(c2_keto, any3_keto, BondType.SINGLE);
        KETO_TAUTOMER.addBond(c2_keto, h_keto, BondType.SINGLE);
    }
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
    public static int countAromaticRings(MoleculeWithAdjacencyList target) {return countSubgraphs(AROMATIC_RING, target);}
    public static int countKeto(MoleculeWithAdjacencyList target) {return countSubgraphs(KETO_TAUTOMER, target);}
    public static int countEnol(MoleculeWithAdjacencyList target) {return countSubgraphs(ENOL_TAUTOMER, target);}

}