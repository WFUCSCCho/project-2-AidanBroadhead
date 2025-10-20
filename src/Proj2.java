/****************************************************************************
        ∗ @file: Proj2.java
        ∗ @description: This program implements . . .
        ∗ @author: Aidan Broadhead
        ∗ @date: October 21, 2025
 ****************************************************************************/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.*;
import java.io.*;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

	// FINISH ME


        // 1) Read up to N songs
        List<Song> data = new ArrayList<>(numLines);
        int count = 0;
        while (inputFileNameScanner.hasNextLine() && count < numLines) {
            String line = inputFileNameScanner.nextLine();
            Song s = parseSong(line);
            if (s != null) {
                data.add(s);
                count++;
            }
        }
        inputFileNameScanner.close();
        inputFileNameStream.close();

        if (data.isEmpty()) {
            System.err.println("No data read. Check file and N.");
            return;
        }

        // 2) Prepare sorted and randomized orders
        List<Song> sorted = new ArrayList<>(data);
        Collections.sort(sorted); // relies on Song.compareTo(name, case-insensitive)
        List<Song> randomized = new ArrayList<>(data);
        Collections.shuffle(randomized, new Random(42)); // reproducible shuffle

        // 3) Make trees
        BST<Song> bstSorted = new BST<>();
        BST<Song> bstRand   = new BST<>();
        AvlTree<Song> avlSorted = new AvlTree<>();
        AvlTree<Song> avlRand   = new AvlTree<>();

        // 4) Time inserts
        long bstInsSorted = timeInsertBST(bstSorted, sorted);
        long bstInsRand   = timeInsertBST(bstRand,   randomized);
        long avlInsSorted = timeInsertAVL(avlSorted, sorted);
        long avlInsRand   = timeInsertAVL(avlRand,   randomized);

        // 5) Time searches (using original order)
        long bstSeaSorted = timeSearchBST(bstSorted, data);
        long bstSeaRand   = timeSearchBST(bstRand,   data);
        long avlSeaSorted = timeSearchAVL(avlSorted, data);
        long avlSeaRand   = timeSearchAVL(avlRand,   data);

        int N = data.size();

        // 6) Pretty-print (seconds + rate)
        System.out.println("N = " + N);

        System.out.println("\nINSERT (seconds):");
        System.out.printf("  BST (sorted) : %.6f%n", nsToSec(bstInsSorted));
        System.out.printf("  BST (random) : %.6f%n", nsToSec(bstInsRand));
        System.out.printf("  AVL (sorted) : %.6f%n", nsToSec(avlInsSorted));
        System.out.printf("  AVL (random) : %.6f%n", nsToSec(avlInsRand));

        System.out.println("\nSEARCH (seconds):");
        System.out.printf("  BST (sorted) : %.6f%n", nsToSec(bstSeaSorted));
        System.out.printf("  BST (random) : %.6f%n", nsToSec(bstSeaRand));
        System.out.printf("  AVL (sorted) : %.6f%n", nsToSec(avlSeaSorted));
        System.out.printf("  AVL (random) : %.6f%n", nsToSec(avlSeaRand));

        System.out.println("\nRATES (seconds per node):");
        System.out.printf("  Insert BST(sorted): %.3e  | AVL(sorted): %.3e%n",
                nsToSec(bstInsSorted)/N, nsToSec(avlInsSorted)/N);
        System.out.printf("  Insert BST(random): %.3e  | AVL(random): %.3e%n",
                nsToSec(bstInsRand)/N, nsToSec(avlInsRand)/N);
        System.out.printf("  Search BST(sorted): %.3e  | AVL(sorted): %.3e%n",
                nsToSec(bstSeaSorted)/N, nsToSec(avlSeaSorted)/N);
        System.out.printf("  Search BST(random): %.3e  | AVL(random): %.3e%n",
                nsToSec(bstSeaRand)/N, nsToSec(avlSeaRand)/N);

        // 7) Append CSV to output.txt
        File out = new File("output.txt");
        boolean writeHeader = !out.exists() || out.length() == 0;
        try (FileWriter fw = new FileWriter(out, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            if (writeHeader) {
                bw.write("N,bst_insert_sorted_ns,bst_insert_random_ns,avl_insert_sorted_ns,avl_insert_random_ns,bst_search_sorted_ns,bst_search_random_ns,avl_search_sorted_ns,avl_search_random_ns");
                bw.newLine();
            }
            bw.write(String.format(Locale.US,
                    "%d,%d,%d,%d,%d,%d,%d,%d,%d",
                    N, bstInsSorted, bstInsRand, avlInsSorted, avlInsRand,
                    bstSeaSorted, bstSeaRand, avlSeaSorted, avlSeaRand));
            bw.newLine();
        }
    }

    // ---- helpers ----

    private static Song parseSong(String line) {
        // Basic CSV split (assumes no embedded commas in fields).
        // If your 'name' can contain commas, we should switch to a real CSV parser or quoted splitting.
        String[] f = line.split(",", -1);
        try {
            // Expecting 14 columns in the order used by Song.toString()
            // id, name, duration, energy, key, loudness, mode, speechiness, acousticness,
            // instrumentalness, liveness, valence, tempo, danceability
            if (f.length < 14) return null;
            String id = f[0].trim();
            String name = f[1].trim();
            double duration = Double.parseDouble(f[2].trim());
            double energy = Double.parseDouble(f[3].trim());
            int key = Integer.parseInt(f[4].trim());
            double loudness = Double.parseDouble(f[5].trim());
            int mode = Integer.parseInt(f[6].trim());
            double speechiness = Double.parseDouble(f[7].trim());
            double acousticness = Double.parseDouble(f[8].trim());
            double instrumentalness = Double.parseDouble(f[9].trim());
            double liveness = Double.parseDouble(f[10].trim());
            double valence = Double.parseDouble(f[11].trim());
            double tempo = Double.parseDouble(f[12].trim());
            double danceability = Double.parseDouble(f[13].trim());

            return new Song(id, name, duration, energy, key, loudness, mode,
                    speechiness, acousticness, instrumentalness, liveness, valence, tempo, danceability);
        } catch (Exception e) {
            // skip malformed line
            return null;
        }
    }

    private static long timeInsertBST(BST<Song> tree, List<Song> items) {
        long start = System.nanoTime();
        for (Song s : items) tree.insertNode(s);
        return System.nanoTime() - start;
    }

    private static long timeInsertAVL(AvlTree<Song> tree, List<Song> items) {
        long start = System.nanoTime();
        for (Song s : items) tree.insert(s);
        return System.nanoTime() - start;
    }

    private static long timeSearchBST(BST<Song> tree, List<Song> items) {
        long start = System.nanoTime();
        for (Song s : items) {
            if (tree.searchNode(s) == null) {
                // optional: sanity check fail handling
                // System.err.println("BST missing: " + s.getName());
            }
        }
        return System.nanoTime() - start;
    }

    private static long timeSearchAVL(AvlTree<Song> tree, List<Song> items) {
        long start = System.nanoTime();
        for (Song s : items) {
            if (!tree.contains(s)) {
                // optional: sanity check fail handling
                // System.err.println("AVL missing: " + s.getName());
            }
        }
        return System.nanoTime() - start;
    }

    private static double nsToSec(long ns) {
        return ns / 1_000_000_000.0;
    }

}